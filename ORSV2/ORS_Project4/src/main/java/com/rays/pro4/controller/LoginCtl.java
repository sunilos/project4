package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.RoleBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Model.UserModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

/**
 * Servlet implementation class LoginCtl
 * 
 * @author Sunil OS
 */

@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })

public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	private static Logger log = Logger.getLogger(LoginCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("LoginCtl Ki Validate Started");
		log.debug("LoginCtl Method validate Started");

		boolean pass = true;
		

		String op = request.getParameter("operation");

		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			System.out.println("LoginCtl Me Operation get " + op);
			
			return pass;
			
			
		}
		
		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			System.out.println("loginctl me is Null Condition ");
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			System.out.println("loginctl me is Email condition");
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login Id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			System.out.println("loginctl me is Pass Null condition");
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		System.out.println("LoginCtl Method validate Ended");
		log.debug("LoginCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("LoginCtl Method populatebean Started");
		System.out.println("Data ko Populate Karwaya ");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));// get kiya loginctl
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Display Login form
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Loginctl Ki Do get  ");
 	HttpSession session = request.getSession(false);
		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_LOG_OUT.equals(op) && !OP_SIGN_IN.equals(op)) {

			System.out.println("LoginCtl ki doGet Operation Logout");

			session.invalidate();

			ServletUtility.setSuccessMessage("User Logout Succesfully", request);

			ServletUtility.forward(getView(), request, response);

			return;
		}
		System.out.println("LoginCtl ki doGet Se Forward");
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Submitting or login action performing
	 *
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		log.debug(" Method doPost Started");
		System.out.println("LoginCtl ki doPost ");
		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		// long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			System.out.println("LoginCtl ki Do post Me Operation SignIn Mila ");
			UserBean bean = (UserBean) populateBean(request);

			try {

				bean = model.authenticate(bean.getLogin(), bean.getPassword());

				String uri = request.getParameter("URI");
				System.out.println("URI LoginCtl Ki Do post Me" + uri);

				if (bean != null) {

					System.out.println(" Loginctl Ki Do post 11");

					session.setAttribute("user", bean);
					long rollId = bean.getRoleId();
                    
					RoleBean rolebean = role.findByPK(rollId);

					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}
 
					if ("null".equalsIgnoreCase(uri)) {
						
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						ServletUtility.redirect(uri, request, response);
						return;
					} 

				} else {
					System.out.println(" Loginctl Ki Do post 22");
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} /*
			 * 
			 * 
			 * else if (OP_LOG_OUT.equals(op)) { System.out.println(" Lctl Do post 44");
			 * 
			 * session = request.getSession(); session.invalidate();
			 * 
			 * ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);
			 * 
			 * return;
			 * 
			 * }
			 */

		else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			System.out.println("Loginctl Ki Do Post Me Opeartion SignUp Mila redirect ");

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);

			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}
}
