package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.FacultyBean;
import com.sunilos.p4.model.FacultyModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/FacultyReportCtl")
public class FacultyReportCtl extends BaseReportCtl<FacultyBean> {

    public List<FacultyBean> getList() {
        FacultyModel model = new FacultyModel();
        @SuppressWarnings("unchecked")
        List<FacultyBean> faculty = model.list();
        return faculty;
    }

    public String getView() {
        return ORSView.FACULTY_REPORT_VIEW;
    }

    public String getCompiledReportKey() {
        return "FACULTY_LIST_COMPILED_REPORT";
    }

}
