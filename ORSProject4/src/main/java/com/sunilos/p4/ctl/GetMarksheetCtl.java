package com.sunilos.p4.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.MarksheetBean;
import com.sunilos.p4.model.MarksheetModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;
import com.sunilos.p4.util.ServletUtility;

/**
 * Get marksheet by given roll number
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

@WebServlet("/ctl/GetMarksheetCtl")
public class GetMarksheetCtl extends BaseCtl<MarksheetBean, MarksheetModel> {

	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String rollNo = DataUtility.getString(request.getParameter("rollNo"));

		MarksheetModel model = getModel();

		MarksheetBean bean = model.findByRollNo(rollNo);
		if (bean != null) {
			ServletUtility.setBean(bean, request);
		} else {
			ServletUtility.setErrorMessage("RollNo does not exist", request);
		}
		ServletUtility.forwardPage(getView(), request, response);
	}

	@Override
	protected String getView() {
		return getView(null);
	}

	@Override
	protected String getView(String op) {
		return ORSView.GET_MARKSHEET_VIEW;
	}

	@Override
	protected MarksheetModel getModel() {
		return new MarksheetModel();
	}

}
