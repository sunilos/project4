package com.sunilos.p4.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.model.UserModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;
import com.sunilos.p4.util.ServletUtility;

/**
 * Change Password functionality Controller. Performs operation for Change
 * Password
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

@WebServlet("/ctl/ChangePasswordCtl")
public class ChangePasswordCtl extends BaseCtl<UserBean, UserModel> {

	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ChangePasswordCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage("New and confirm passwords not matched", request);

			pass = false;
		}

		log.debug("ChangePasswordCtl Method validate Ended");

		return pass;
	}

	@Override
	protected UserBean populateBean(HttpServletRequest request) {
		log.debug("ChangePasswordCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		populateDTO(bean, request);

		log.debug("ChangePasswordCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("ChangePasswordCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		String oldPassword = DataUtility.getString(request.getParameter("oldPassword"));
		String newPassword = DataUtility.getString(request.getParameter("newPassword"));
		String confirmPassword = DataUtility.getString(request.getParameter("confirmPassword"));

		UserBean bean = populateBean(request);

		if (!newPassword.equals(confirmPassword)) {
			ServletUtility.setErrorMessage("New passwodr and confirmed password does not match", request);
			ServletUtility.setBean(bean, request);
			ServletUtility.forwardPage(getView(op), request, response);
			return;
		}

		// get model
		UserModel model = getModel();
		HttpSession session = request.getSession(true);
		UserBean UserBean = (UserBean) session.getAttribute("user");
		long id = UserBean.getId();

		try {
			boolean flag = model.changePassword(id, oldPassword, newPassword);

			if (flag == true) {
				bean = model.findByLogin(UserBean.getLogin());
				session.setAttribute("user", bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Password has been changed Successfully.", request);
			}
		} catch (Exception e) {
			ServletUtility.setErrorMessage(e.getMessage(), request);
		}
		ServletUtility.forwardPage(getView(op), request, response);

		log.debug("ChangePasswordCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		return getView(null);
	}

	@Override
	protected String getView(String op) {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

	@Override
	protected UserModel getModel() {
		return new UserModel();
	}

}
