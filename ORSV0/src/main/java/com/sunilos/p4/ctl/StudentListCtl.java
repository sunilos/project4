package com.sunilos.p4.ctl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.StudentBean;
import com.sunilos.p4.model.StudentModel;
import com.sunilos.p4.util.DataUtility;

/**
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

@WebServlet("/ctl/StudentListCtl")
public class StudentListCtl extends BaseListCtl<StudentBean, StudentModel> {

	private static Logger log = Logger.getLogger(StudentListCtl.class);

	@Override
	protected StudentBean populateBean(HttpServletRequest request) {
		StudentBean bean = new StudentBean();
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		return bean;
	}

	@Override
	protected String getView() {
		return getView(null);
	}

	@Override
	protected String getView(String op) {
		return ORSView.STUDENT_LIST_VIEW;
	}

	@Override
	protected StudentModel getModel() {
		return new StudentModel();
	}

}