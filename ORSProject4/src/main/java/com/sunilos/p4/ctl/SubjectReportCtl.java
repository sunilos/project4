package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.SubjectBean;
import com.sunilos.p4.model.SubjectModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/SubjectReportCtl")
public class SubjectReportCtl extends BaseReportCtl<SubjectBean> {

    public List<SubjectBean> getList() {
        SubjectModel model = new SubjectModel();
        @SuppressWarnings("unchecked")
        List<SubjectBean> subjects = model.list();
        return subjects;
    }

    public String getView() {
        return ORSView.SUBJECT_REPORT_VIEW;
    }

    public String getCompiledReportKey() {
        return "SUBJECT_LIST_COMPILED_REPORT";
    }

}
