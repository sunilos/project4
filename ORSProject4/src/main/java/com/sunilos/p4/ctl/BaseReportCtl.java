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

public abstract class BaseReportCtl<B extends BaseBean> extends HttpServlet {

    private static final Logger log = Logger.getLogger(BaseReportCtl.class);

    public abstract String getView();

    public abstract String getCompiledReportKey();

    public abstract List<B> getList();

    public static final String PDF = "pdf";

    public static final String DOC = "doc";

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
