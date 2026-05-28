package com.sunilos.p4.ctl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.MarksheetBean;
import com.sunilos.p4.model.MarksheetModel;

@WebServlet("/ctl/MarksheetReportCtl")
public class MarksheetReportCtl extends HttpServlet {

    private static final Logger log = Logger.getLogger(MarksheetReportCtl.class);
    private static final String COMPILED_REPORT_KEY = "MARKSHEET_LIST_COMPILED_REPORT";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JasperReport jasperReport = getCompiledReport();

            MarksheetModel model = new MarksheetModel();
            @SuppressWarnings("unchecked")
            List<MarksheetBean> marksheets = model.list();

            Map<String, Object> params = new HashMap<>();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(marksheets);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=MarksheetList.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            log.error("Marksheet report generation failed", e);
            throw new ServletException("Failed to generate marksheet report: " + e.getMessage(), e);
        }
    }

    private JasperReport getCompiledReport() throws Exception {
        JasperReport cached = (JasperReport) getServletContext().getAttribute(COMPILED_REPORT_KEY);
        if (cached != null) return cached;
        InputStream jrxml = getClass().getResourceAsStream("/reports/MarksheetListReport.jrxml");
        if (jrxml == null) throw new IllegalStateException("Report template not found: /reports/MarksheetListReport.jrxml");
        JasperReport compiled = JasperCompileManager.compileReport(jrxml);
        getServletContext().setAttribute(COMPILED_REPORT_KEY, compiled);
        return compiled;
    }
}
