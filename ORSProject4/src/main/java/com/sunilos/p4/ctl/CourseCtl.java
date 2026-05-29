package com.sunilos.p4.ctl;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.CourseBean;
import com.sunilos.p4.model.CourseModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/ctl/CourseCtl")
public class CourseCtl extends BaseCtl<CourseBean, CourseModel> {

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(CourseCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("CourseCtl Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("duration"))) {
            request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
            pass = false;
        }

        log.debug("CourseCtl Method validate Ended");

        return pass;
    }

    @Override
    protected CourseBean populateBean(HttpServletRequest request) {

        log.debug("CourseCtl Method populatebean Started");

        CourseBean bean = new CourseBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));

        populateDTO(bean, request);

        log.debug("CourseCtl Method populatebean Ended");

        return bean;
    }

    @Override
    protected String getView() {
        return getView(null);
    }

    @Override
    protected String getView(String op) {
        if (OP_CANCEL.equalsIgnoreCase(op) || OP_DELETE.equalsIgnoreCase(op)) {
            return ORSView.COURSE_LIST_CTL;
        } else {
            return ORSView.COURSE_VIEW;
        }
    }

    @Override
    protected CourseModel getModel() {
        return new CourseModel();
    }

}
