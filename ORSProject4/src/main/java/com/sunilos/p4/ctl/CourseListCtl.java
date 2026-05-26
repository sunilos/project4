package com.sunilos.p4.ctl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.CourseBean;
import com.sunilos.p4.model.CourseModel;
import com.sunilos.p4.util.DataUtility;

@WebServlet("/ctl/CourseListCtl")
public class CourseListCtl extends BaseListCtl<CourseBean, CourseModel> {

    private static Logger log = Logger.getLogger(CourseListCtl.class);

    @Override
    protected CourseBean populateBean(HttpServletRequest request) {
        CourseBean bean = new CourseBean();
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));
        return bean;
    }

    @Override
    protected String getView() {
        return getView();
    }

    @Override
    protected String getView(String op) {
        return ORSView.COURSE_LIST_VIEW;
    }

    @Override
    protected CourseModel getModel() {
        return new CourseModel();
    }

}
