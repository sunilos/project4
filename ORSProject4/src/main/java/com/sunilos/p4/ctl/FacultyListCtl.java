package com.sunilos.p4.ctl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.FacultyBean;
import com.sunilos.p4.model.FacultyModel;
import com.sunilos.p4.util.DataUtility;

@WebServlet("/ctl/FacultyListCtl")
public class FacultyListCtl extends BaseListCtl<FacultyBean, FacultyModel> {

    private static Logger log = Logger.getLogger(FacultyListCtl.class);

    @Override
    protected FacultyBean populateBean(HttpServletRequest request) {
        FacultyBean bean = new FacultyBean();
        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));
        bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
        return bean;
    }

    @Override
    protected String getView() { return getView(null); }

    @Override
    protected String getView(String op) { return ORSView.FACULTY_LIST_VIEW; }

    @Override
    protected FacultyModel getModel() { return new FacultyModel(); }
}
