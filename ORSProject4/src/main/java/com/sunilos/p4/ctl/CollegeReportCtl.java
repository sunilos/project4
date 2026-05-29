package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.CollegeBean;
import com.sunilos.p4.model.CollegeModel;

import jakarta.servlet.annotation.WebServlet;

/**
 * Report servlet that generates a College list report in PDF or DOC format.
 * Mapped to {@code /ctl/CollegeReportCtl}; add {@code ?type=doc} for Word output.
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseReportCtl
 */
@WebServlet("/ctl/CollegeReportCtl")
public class CollegeReportCtl extends BaseReportCtl<CollegeBean> {

    /**
     * Fetches all colleges from the database.
     *
     * @return list of all {@link CollegeBean} records
     */
    public List<CollegeBean> getList() {
        CollegeModel model = new CollegeModel();
        @SuppressWarnings("unchecked")
        List<CollegeBean> colleges = model.list();
        return colleges;
    }

    /**
     * Returns the JRXML template path for the college list report.
     *
     * @return {@link ORSView#COLLEGE_REPORT_VIEW}
     */
    public String getView() {
        return ORSView.COLLEGE_REPORT_VIEW;
    }

    /**
     * Returns the ServletContext cache key for the compiled college report.
     *
     * @return {@code "COLLEGE_LIST_COMPILED_REPORT"}
     */
    public String getCompiledReportKey() {
        return "COLLEGE_LIST_COMPILED_REPORT";
    }

}
