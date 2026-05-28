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

import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.model.RoleModel;

@WebServlet("/ctl/RoleReportCtl")
public class RoleReportCtl extends HttpServlet {

    private static final Logger log = Logger.getLogger(RoleReportCtl.class);
    private static final String COMPILED_REPORT_KEY = "ROLE_LIST_COMPILED_REPORT";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JasperReport jasperReport = getCompiledReport();

            RoleModel model = new RoleModel();
            @SuppressWarnings("unchecked")
            List<RoleBean> roles = model.list();

            Map<String, Object> params = new HashMap<>();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(roles);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=RoleList.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            log.error("Role report generation failed", e);
            throw new ServletException("Failed to generate role report: " + e.getMessage(), e);
        }
    }

    private JasperReport getCompiledReport() throws Exception {
        JasperReport cached = (JasperReport) getServletContext().getAttribute(COMPILED_REPORT_KEY);
        if (cached != null) return cached;
        InputStream jrxml = getClass().getResourceAsStream("/reports/RoleListReport.jrxml");
        if (jrxml == null) throw new IllegalStateException("Report template not found: /reports/RoleListReport.jrxml");
        JasperReport compiled = JasperCompileManager.compileReport(jrxml);
        getServletContext().setAttribute(COMPILED_REPORT_KEY, compiled);
        return compiled;
    }
}
