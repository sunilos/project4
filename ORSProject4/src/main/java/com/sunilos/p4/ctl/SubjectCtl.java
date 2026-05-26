package com.sunilos.p4.ctl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.SubjectBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.model.CourseModel;
import com.sunilos.p4.model.SubjectModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;

import java.util.List;

@WebServlet("/ctl/SubjectCtl")
public class SubjectCtl extends BaseCtl<SubjectBean, SubjectModel> {

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(SubjectCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {
        CourseModel model = new CourseModel();
        try {
            List l = model.list();
            request.setAttribute("courseList", l);
        } catch (ApplicationException e) {
            log.error(e);
        }
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("SubjectCtl Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
            pass = false;
        }

        log.debug("SubjectCtl Method validate Ended");

        return pass;
    }

    @Override
    protected SubjectBean populateBean(HttpServletRequest request) {

        log.debug("SubjectCtl Method populatebean Started");

        SubjectBean bean = new SubjectBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

        populateDTO(bean, request);

        log.debug("SubjectCtl Method populatebean Ended");

        return bean;
    }

    @Override
    protected String getView() {
        return getView(null);
    }

    @Override
    protected String getView(String op) {
        if (OP_CANCEL.equalsIgnoreCase(op) || OP_DELETE.equalsIgnoreCase(op)) {
            return ORSView.SUBJECT_LIST_CTL;
        } else {
            return ORSView.SUBJECT_VIEW;
        }
    }

    @Override
    protected SubjectModel getModel() {
        return new SubjectModel();
    }

}
