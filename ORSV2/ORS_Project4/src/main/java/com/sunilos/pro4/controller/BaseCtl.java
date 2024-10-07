 package com.sunilos.pro4.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunilos.pro4.bean.BaseBean;
import com.sunilos.pro4.bean.UserBean;
import com.sunilos.pro4.util.DataUtility;
import com.sunilos.pro4.util.DataValidator;
import com.sunilos.pro4.util.ServletUtility;

/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 *
 * @author Sunil OS
 *
 */

public abstract class BaseCtl extends HttpServlet {

	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_LOG_OUT = "Logout";
	public static final String OP_RESET = "Reset";
	public static final String OP_UPDATE = "Update";

	/**
	 * Success message key constant
	 */
	public static final String MSG_SUCCESS = "success";

	/**
	 * Error message key constant
	 */
	public static final String MSG_ERROR = "error";

	/**
	 * Validates input data entered by User
	 *
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	/**
	 * Loads list and other data required to display at HTML form
	 *
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {

	}

	/**
	 * Populates bean object from request parameters
	 *
	 * @param request
	 * @return
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		return null;
	}

	/**
	 * Populates Generic attributes in DTO
	 *
	 * @param dto
	 * @param request
	 * @return
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");

		if (userbean == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";
		} else {

			modifiedBy = userbean.getLogin();

			// If record is created first time
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}

		}

		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

		return dto;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Bctl ki Service Method ");

		// Load the preloaded data required to display at HTML form
		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("Bctl Mai Operation Get Kiya = " +" "+ op);
		// Check if operation is not DELETE, VIEW, CANCEL, RESET and NULL then
		// perform input data validation

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			System.out.println("Bctl Me Condition Check Kri");
			// Check validation, If fail then send back to page with error
			// messages
			if (!validate(request)) {
				System.out.println("Bctl Me validation Perform Hua ");

			BaseBean bean = (BaseBean) populateBean(request);

				// wapis se inserted data dikhe jo phle in put kiya tha

				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);

			 return;
			}
		}
		System.out.println("Bctl Ki  Super Service ");
		super.service(request, response);

	}

	/**
	 * Returns the VIEW page of this Controller
	 *
	 * @return
	 */
	protected abstract String getView();

}
