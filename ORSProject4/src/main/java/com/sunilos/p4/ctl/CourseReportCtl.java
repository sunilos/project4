package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.CourseBean;
import com.sunilos.p4.model.CourseModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/CourseReportCtl")
public class CourseReportCtl extends BaseReportCtl<CourseBean> {

    public List<CourseBean> getList() {
        CourseModel model = new CourseModel();
        @SuppressWarnings("unchecked")
        List<CourseBean> courses = model.list();
        return courses;
    }

    public String getView() {
        return ORSView.COURSE_REPORT_VIEW;
    }

    public String getCompiledReportKey() {
        return "COURSE_LIST_COMPILED_REPORT";
    }

}
