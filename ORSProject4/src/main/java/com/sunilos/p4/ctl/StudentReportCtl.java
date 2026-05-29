package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.StudentBean;
import com.sunilos.p4.model.StudentModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/StudentReportCtl")
public class StudentReportCtl extends BaseReportCtl<StudentBean> {

    public List<StudentBean> getList() {
        StudentModel model = new StudentModel();
        @SuppressWarnings("unchecked")
        List<StudentBean> students = model.list();
        return students;
    }

    public String getView() {
        return ORSView.STUDENT_REPORT_VIEW;
    }

    public String getCompiledReportKey() {
        return "STUDENT_LIST_COMPILED_REPORT";
    }

}
