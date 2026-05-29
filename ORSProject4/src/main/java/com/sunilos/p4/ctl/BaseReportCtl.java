package com.sunilos.p4.ctl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.BaseBean;
import com.sunilos.p4.util.DataUtility;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;

/**
 * Abstract base servlet for JasperReports-based list reports.
 * <p>
 * Subclasses implement three methods to produce a fully functional report
 * servlet: {@link #getList()} supplies the data, {@link #getView()} points to
 * the JRXML template, and {@link #getCompiledReportKey()} provides the
 * ServletContext cache key so the template is compiled only once per
 * application lifecycle.
 * <p>
 * Supports two output formats selected via the {@code type} request parameter:
 * <ul>
 *   <li>{@code type=pdf} (default) — inline PDF via JasperExportManager</li>
 *   <li>{@code type=doc} — OOXML Word document via JRDocxExporter</li>
 * </ul>
 *
 * @param <B> the bean type whose instances populate the report data source;
 *            must extend {@link BaseBean}
 *
 * @author Rays EdTech
 * @version 1.0
 */
public abstract class BaseReportCtl<B extends BaseBean> extends HttpServlet {

    private static final Logger log = Logger.getLogger(BaseReportCtl.class);

    /** Output format constant for PDF reports. */
    public static final String PDF = "pdf";

    /** Output format constant for Word (OOXML .docx) reports. */
    public static final String DOC = "doc";

    /**
     * Returns the classpath-relative path to the JRXML report template.
     * Example: {@code "/reports/CourseListReport.jrxml"}
     *
     * @return non-null path to the JRXML resource
     */
    public abstract String getView();

    /**
     * Returns the unique key used to cache the compiled {@link JasperReport}
     * in the {@link jakarta.servlet.ServletContext}.
     * Example: {@code "COURSE_LIST_COMPILED_REPORT"}
     *
     * @return non-null, application-unique cache key
     */
    public abstract String getCompiledReportKey();

    /**
     * Fetches and returns the list of beans that will be used as the report
     * data source. Called once per request.
     *
     * @return non-null list of beans; may be empty
     */
    public abstract List<B> getList();

    /**
     * Returns the compiled {@link JasperReport} for the given JRXML path,
     * compiling it on the first call and caching the result in the
     * {@link jakarta.servlet.ServletContext} under {@link #getCompiledReportKey()}.
     * Thread-safe: synchronizes on the ServletContext to prevent duplicate
     * compilation under concurrent first requests.
     *
     * @param reportTemplatePath classpath-relative path to the JRXML file
     * @return compiled report ready for filling
     * @throws IllegalStateException if the JRXML resource cannot be located
     * @throws Exception             if JasperReports compilation fails
     */
    public JasperReport getCompiledReport(String reportTemplatePath) throws Exception {
        synchronized (getServletContext()) {
            JasperReport cached = (JasperReport) getServletContext().getAttribute(getCompiledReportKey());
            if (cached != null)
                return cached;
            try (InputStream jrxml = getClass().getResourceAsStream(reportTemplatePath)) {
                if (jrxml == null)
                    throw new IllegalStateException("Report template not found: " + reportTemplatePath);
                JasperReport compiled = JasperCompileManager.compileReport(jrxml);
                getServletContext().setAttribute(getCompiledReportKey(), compiled);
                return compiled;
            }
        }
    }

    /**
     * Fills the compiled report with {@code list} as its data source and writes
     * the output to the HTTP response. The {@code reportType} parameter selects
     * the export format: {@value #PDF} produces an inline PDF and {@value #DOC}
     * produces an OOXML Word document.
     *
     * @param response           the HTTP response to write the report into
     * @param list               the data to populate the report
     * @param reportTemplatePath classpath-relative path to the JRXML file
     * @param reportType         {@value #PDF} or {@value #DOC}
     * @throws Exception if report filling or export fails
     */
    private void generateReport(HttpServletResponse response, List<B> list, String reportTemplatePath,
            String reportType)
            throws Exception {
        JasperReport jasperReport = getCompiledReport(reportTemplatePath);
        Map<String, Object> params = new HashMap<>();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        if (DOC.equals(reportType)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "inline; filename=\"" + getClass().getSimpleName() + ".docx\"");
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            exporter.setConfiguration(new SimpleDocxReportConfiguration());
            exporter.exportReport();
        } else {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + getClass().getSimpleName() + ".pdf\"");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        }
    }

    /**
     * Handles GET requests by fetching the data list, determining the requested
     * output format from the {@code type} parameter (defaults to {@value #PDF}),
     * and delegating to {@link #generateReport}.
     *
     * @param request  the HTTP request; reads optional {@code type} parameter
     * @param response the HTTP response the report is written to
     * @throws ServletException if report generation fails
     * @throws IOException      if the response output stream cannot be written
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<B> list = getList();
            String reportType = request.getParameter("type");
            if (!DOC.equals(reportType)) {
                reportType = PDF;
            }
            generateReport(response, list, getView(), reportType);
        } catch (Exception e) {
            log.error(getClass().getSimpleName() + " report generation failed", e);
            throw new ServletException("Failed to generate report: " + e.getMessage(), e);
        }
    }

    /**
     * Delegates POST requests to {@link #doGet}, so the report can be triggered
     * from either a link or a form submission.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if report generation fails
     * @throws IOException      if the response output stream cannot be written
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
