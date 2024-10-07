package com.sunilos.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.pro4.bean.BaseBean;
import com.sunilos.pro4.bean.UserBean;
import com.sunilos.pro4.exception.ApplicationException;
import com.sunilos.pro4.exception.RecordNotFoundException;
import com.sunilos.pro4.model.UserModel;
import com.sunilos.pro4.util.DataUtility;
import com.sunilos.pro4.util.DataValidator;
import com.sunilos.pro4.util.PropertyReader;
import com.sunilos.pro4.util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
 * Forget Password functionality Controller. Performs operation for Forget
 * Password
 * 
 * @author Sunil OS
 */
@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {

	/** The log. */
	private static Logger log = Logger.getLogger(ForgetPasswordCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("ForgetPasswordCtl Method validate Started");
		log.debug("ForgetPasswordCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login Id"));
			pass = false;
		}
		log.debug("ForgetPasswordCtl Method validate Ended");
		System.out.println("ForgetPasswordCtl Method validate Ended");
		return pass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ForgetPasswordCtl Method populatebean Started");
		System.out.println("ForgetPasswordCtl Method populatebean Started");
		UserBean bean = new UserBean();

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		log.debug("ForgetPasswordCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains Display logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ForgetPasswordCtl Method doGet Started");
		System.out.println("ForgetPasswordCtl Method doGet Se Forward");

		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Contains Submit logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ForgetPasswordCtl Method doPost Started");
		System.out.println("ForgetPasswordCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		UserBean bean = (UserBean) populateBean(request);

// get model
		UserModel model = new UserModel();

		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				System.out.println("FPCTL Operation go Mila");
				model.forgetPassword(bean.getLogin());
				ServletUtility.setSuccessMessage("Password has been sent to your email id.", request);
			} catch (RecordNotFoundException e) {
				System.out.println("FPCTL  Set Error Msg ");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id does'nt Exists", request);
				// ServletUtility.forward(getView(), request, response);
				log.error(e);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			System.out.println("FPCTL doPost Se Redirect FPCTL Par");
			ServletUtility.redirect(ORSView.FORGET_PASSWORD_CTL, request, response);
			return;
		}
		log.debug("ForgetPasswordCtl Method doPost Ended");
		ServletUtility.forward(getView(), request, response);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
