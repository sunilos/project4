package com.sunilos.p4.ctl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.model.RoleModel;
import com.sunilos.p4.util.DataUtility;

/**
 * Role List functionality Controller. Performs operation for list, search
 * operations of Role
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

@WebServlet("/ctl/RoleListCtl")
public class RoleListCtl extends BaseListCtl<RoleBean, RoleModel> {

	private static Logger log = Logger.getLogger(RoleListCtl.class);

	@Override
	protected RoleBean populateBean(HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		return bean;
	}

	@Override
	protected String getView() {
		return getView();
	}

	@Override
	protected String getView(String op) {
		return ORSView.ROLE_LIST_VIEW;
	}

	@Override
	protected RoleModel getModel() {
		return new RoleModel();
	}

}