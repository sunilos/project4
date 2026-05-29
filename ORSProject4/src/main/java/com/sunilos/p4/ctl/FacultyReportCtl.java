package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.FacultyBean;
import com.sunilos.p4.model.FacultyModel;

import jakarta.servlet.annotation.WebServlet;

/**
 * Report servlet that generates a Faculty list report in PDF or DOC format.
 * Mapped to {@code /ctl/FacultyReportCtl}; add {@code ?type=doc} for Word output.
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseReportCtl
 */
@WebServlet("/ctl/FacultyReportCtl")
public class FacultyReportCtl extends BaseReportCtl<FacultyBean> {

    /**
     * Fetches all faculty members from the database.
     *
     * @return list of all {@link FacultyBean} records
     */
    public List<FacultyBean> getList() {
        FacultyModel model = new FacultyModel();
        @SuppressWarnings("unchecked")
        List<FacultyBean> faculty = model.list();
        return faculty;
    }

    /**
     * Returns the JRXML template path for the faculty list report.
     *
     * @return {@link ORSView#FACULTY_REPORT_VIEW}
     */
    public String getView() {
        return ORSView.FACULTY_REPORT_VIEW;
    }

    /**
     * Returns the ServletContext cache key for the compiled faculty report.
     *
     * @return {@code "FACULTY_LIST_COMPILED_REPORT"}
     */
    public String getCompiledReportKey() {
        return "FACULTY_LIST_COMPILED_REPORT";
    }

}
