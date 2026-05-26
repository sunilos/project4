package com.sunilos.p4.ctl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.SubjectBean;
import com.sunilos.p4.model.SubjectModel;
import com.sunilos.p4.util.DataUtility;

@WebServlet("/ctl/SubjectListCtl")
public class SubjectListCtl extends BaseListCtl<SubjectBean, SubjectModel> {

    private static Logger log = Logger.getLogger(SubjectListCtl.class);

    @Override
    protected SubjectBean populateBean(HttpServletRequest request) {
        SubjectBean bean = new SubjectBean();
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        return bean;
    }

    @Override
    protected String getView() {
        return getView();
    }

    @Override
    protected String getView(String op) {
        return ORSView.SUBJECT_LIST_VIEW;
    }

    @Override
    protected SubjectModel getModel() {
        return new SubjectModel();
    }

}
