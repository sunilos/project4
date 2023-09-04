package com.sunilos.p4.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.model.RoleModel;
import com.sunilos.p4.model.UserModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;
import com.sunilos.p4.util.ServletUtility;

/**
 * Login functionality Controller. Performs operation for Login
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

@WebServlet("/LoginCtl")
public class LoginCtl extends BaseCtl<UserBean, UserModel> {

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	private static Logger log = Logger.getLogger(LoginCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("LoginCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}

		log.debug("LoginCtl Method validate Ended");

		return pass;
	}

	@Override
	protected UserBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatebean Ended");

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session.invalidate();
		super.doGet(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug(" Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		String login = DataUtility.getString(request.getParameter("login"));
		String password = DataUtility.getString(request.getParameter("password"));

		UserModel model = getModel();
		UserBean bean = model.authenticate(login, password);

		// if user is not found
		if (bean == null) {
			bean = populateBean(request);
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Invalid Login/Password, try again", request);
			ServletUtility.forwardPage(getView(), request, response);
			return;
		}

		HttpSession session = request.getSession(true);
		session.setAttribute("user", bean);

		long rollId = bean.getRoleId();
		RoleModel role = new RoleModel();
		RoleBean rolebean = role.findByPK(rollId);
		if (rolebean != null) {
			session.setAttribute("role", rolebean.getName());
		} else {
			session.setAttribute("role", "invalid role id " + rollId);
		}

		ServletUtility.forwardPage(ORSView.WELCOME_VIEW, request, response);

	}

	@Override
	protected String getView() {
		return getView(null);
	}

	@Override
	protected UserModel getModel() {
		return new UserModel();
	}

	@Override
	protected String getView(String op) {
		return ORSView.LOGIN_VIEW;
	}

}