package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.CourseBean;
import com.sunilos.p4.model.CourseModel;

import jakarta.servlet.annotation.WebServlet;

/**
 * Report servlet that generates a Course list report in PDF or DOC format.
 * Mapped to {@code /ctl/CourseReportCtl}; add {@code ?type=doc} for Word output.
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseReportCtl
 */
@WebServlet("/ctl/CourseReportCtl")
public class CourseReportCtl extends BaseReportCtl<CourseBean> {

    /**
     * Fetches all courses from the database.
     *
     * @return list of all {@link CourseBean} records
     */
    public List<CourseBean> getList() {
        CourseModel model = new CourseModel();
        @SuppressWarnings("unchecked")
        List<CourseBean> courses = model.list();
        return courses;
    }

    /**
     * Returns the JRXML template path for the course list report.
     *
     * @return {@link ORSView#COURSE_REPORT_VIEW}
     */
    public String getView() {
        return ORSView.COURSE_REPORT_VIEW;
    }

    /**
     * Returns the ServletContext cache key for the compiled course report.
     *
     * @return {@code "COURSE_LIST_COMPILED_REPORT"}
     */
    public String getCompiledReportKey() {
        return "COURSE_LIST_COMPILED_REPORT";
    }

}
