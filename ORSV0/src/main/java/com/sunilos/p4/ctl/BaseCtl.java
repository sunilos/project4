package com.sunilos.p4.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.BaseBean;
import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.model.BaseModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.ServletUtility;

/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

public abstract class BaseCtl<B extends BaseBean, M extends BaseModel> extends HttpServlet {

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

	/**
	 * Success message key constant
	 */
	public static final String MSG_SUCCESS = "success";

	/**
	 * Error message key constant
	 */
	public static final String MSG_ERROR = "error";

	private static Logger log = Logger.getLogger(BaseCtl.class);

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
	protected B populateBean(HttpServletRequest request) {
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

		// Load the necessary preloaded data for displaying in an HTML form
		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		/*
		 * // Handle cancel operation if (OP_CANCEL.equalsIgnoreCase(op)) {
		 * ServletUtility.redirect(getView(op), request, response); return; } else if
		 * (OP_DELETE.equalsIgnoreCase(op)) { // Handle Delete operation
		 * doDelete(request, response); return; }
		 */
		BaseBean bean = populateBean(request);

		// Perform validation if form is submitted using POST method
		if ("POST".equals(request.getMethod()) && !validate(request)) {
			ServletUtility.setBean(bean, request);
			ServletUtility.forwardPage(getView(), request, response);
			return;
		}

		try {
			super.service(request, response);
		} catch (DuplicateRecordException e) {
			// Handle if any duplicate record exception
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage(e.getMessage(), request);
			ServletUtility.forwardPage(getView(), request, response);
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			BaseBean bean = getModel().findByPK(id);
			ServletUtility.setBean(bean, request);
		}

		ServletUtility.forwardPage(getView(), request, response);

	}

	/**
	 * Contains submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("CollegeCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		long id = DataUtility.getLong(request.getParameter("id"));

		B bean = populateBean(request);

		// If primary key does exist then update the record of save the record
		if (id > 0) {
			getModel().update(bean);
		} else {
			long pk = getModel().add(bean);
			bean.setId(pk);
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.setSuccessMessage("Data is successfully saved", request);
		ServletUtility.forwardPage(getView(), request, response);

	}

	/**
	 * Delete a record
	 * 
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			BaseBean bean = populateBean(request);
			this.getModel().delete(bean);
			ServletUtility.redirect(getView(OP_DELETE), request, response);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
		}
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected abstract String getView();

	/**
	 * Return View
	 * 
	 * @param op
	 * @return
	 */
	protected abstract String getView(String op);

	/**
	 * Get model of the controller
	 * 
	 * @return
	 */
	protected abstract M getModel();
}
