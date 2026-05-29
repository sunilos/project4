package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.SubjectBean;
import com.sunilos.p4.model.SubjectModel;

import jakarta.servlet.annotation.WebServlet;

/**
 * Report servlet that generates a Subject list report in PDF or DOC format.
 * Mapped to {@code /ctl/SubjectReportCtl}; add {@code ?type=doc} for Word output.
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseReportCtl
 */
@WebServlet("/ctl/SubjectReportCtl")
public class SubjectReportCtl extends BaseReportCtl<SubjectBean> {

    /**
     * Fetches all subjects from the database.
     *
     * @return list of all {@link SubjectBean} records
     */
    public List<SubjectBean> getList() {
        SubjectModel model = new SubjectModel();
        @SuppressWarnings("unchecked")
        List<SubjectBean> subjects = model.list();
        return subjects;
    }

    /**
     * Returns the JRXML template path for the subject list report.
     *
     * @return {@link ORSView#SUBJECT_REPORT_VIEW}
     */
    public String getView() {
        return ORSView.SUBJECT_REPORT_VIEW;
    }

    /**
     * Returns the ServletContext cache key for the compiled subject report.
     *
     * @return {@code "SUBJECT_LIST_COMPILED_REPORT"}
     */
    public String getCompiledReportKey() {
        return "SUBJECT_LIST_COMPILED_REPORT";
    }

}
