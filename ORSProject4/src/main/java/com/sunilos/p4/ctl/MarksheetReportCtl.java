package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.MarksheetBean;
import com.sunilos.p4.model.MarksheetModel;

import jakarta.servlet.annotation.WebServlet;

/**
 * Report servlet that generates a Marksheet list report in PDF or DOC format.
 * Mapped to {@code /ctl/MarksheetReportCtl}; add {@code ?type=doc} for Word output.
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseReportCtl
 */
@WebServlet("/ctl/MarksheetReportCtl")
public class MarksheetReportCtl extends BaseReportCtl<MarksheetBean> {

    /**
     * Fetches all marksheets from the database.
     *
     * @return list of all {@link MarksheetBean} records
     */
    public List<MarksheetBean> getList() {
        MarksheetModel model = new MarksheetModel();
        @SuppressWarnings("unchecked")
        List<MarksheetBean> marksheets = model.list();
        return marksheets;
    }

    /**
     * Returns the JRXML template path for the marksheet list report.
     *
     * @return {@link ORSView#MARKSHEET_REPORT_VIEW}
     */
    public String getView() {
        return ORSView.MARKSHEET_REPORT_VIEW;
    }

    /**
     * Returns the ServletContext cache key for the compiled marksheet report.
     *
     * @return {@code "MARKSHEET_LIST_COMPILED_REPORT"}
     */
    public String getCompiledReportKey() {
        return "MARKSHEET_LIST_COMPILED_REPORT";
    }

}
