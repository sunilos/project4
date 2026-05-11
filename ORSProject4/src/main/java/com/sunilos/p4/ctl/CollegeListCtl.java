package com.sunilos.p4.ctl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.CollegeBean;
import com.sunilos.p4.model.CollegeModel;
import com.sunilos.p4.util.DataUtility;

/**
 * College List functionality Controller. Performs operation for list, search
 * and delete operations of College
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

@WebServlet("/ctl/CollegeListCtl")
public class CollegeListCtl extends BaseListCtl<CollegeBean, CollegeModel> {

	private static Logger log = Logger.getLogger(CollegeListCtl.class);

	@Override
	protected CollegeBean populateBean(HttpServletRequest request) {
		CollegeBean bean = new CollegeBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		return bean;
	}

	@Override
	protected String getView() {
		return getView(null);
	}

	@Override
	protected String getView(String op) {
		return ORSView.COLLEGE_LIST_VIEW;
	}

	@Override
	protected CollegeModel getModel() {
		return new CollegeModel();
	}

}