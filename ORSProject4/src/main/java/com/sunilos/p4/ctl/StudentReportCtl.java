package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.StudentBean;
import com.sunilos.p4.model.StudentModel;

import jakarta.servlet.annotation.WebServlet;

/**
 * Report servlet that generates a Student list report in PDF or DOC format.
 * Mapped to {@code /ctl/StudentReportCtl}; add {@code ?type=doc} for Word output.
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseReportCtl
 */
@WebServlet("/ctl/StudentReportCtl")
public class StudentReportCtl extends BaseReportCtl<StudentBean> {

    /**
     * Fetches all students from the database.
     *
     * @return list of all {@link StudentBean} records
     */
    public List<StudentBean> getList() {
        StudentModel model = new StudentModel();
        @SuppressWarnings("unchecked")
        List<StudentBean> students = model.list();
        return students;
    }

    /**
     * Returns the JRXML template path for the student list report.
     *
     * @return {@link ORSView#STUDENT_REPORT_VIEW}
     */
    public String getView() {
        return ORSView.STUDENT_REPORT_VIEW;
    }

    /**
     * Returns the ServletContext cache key for the compiled student report.
     *
     * @return {@code "STUDENT_LIST_COMPILED_REPORT"}
     */
    public String getCompiledReportKey() {
        return "STUDENT_LIST_COMPILED_REPORT";
    }

}
