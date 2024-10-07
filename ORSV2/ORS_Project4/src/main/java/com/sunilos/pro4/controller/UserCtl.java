 package com.sunilos.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.pro4.bean.BaseBean;
import com.sunilos.pro4.bean.UserBean;
import com.sunilos.pro4.exception.ApplicationException;
import com.sunilos.pro4.exception.DuplicateRecordException;
import com.sunilos.pro4.model.RoleModel;
import com.sunilos.pro4.model.UserModel;
import com.sunilos.pro4.util.DataUtility;
import com.sunilos.pro4.util.DataValidator;
import com.sunilos.pro4.util.PropertyReader;
import com.sunilos.pro4.util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class UserCtl.
 * 
 * @author Sunil OS
 * 
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(UserCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("uctl preload");
		RoleModel model = new RoleModel();
		try {
			List l = model.list();
			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");
		log.debug("UserCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName","First name must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Last name must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("login"))
				&& !DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {

		} else {
			if (DataValidator.isNull(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
				pass = false;
			} else if (!DataValidator.isPassword(request.getParameter("password"))) {
				request.setAttribute("password",
						"Password contain 8 letters with alpha-numeric,capital latter & special Character");
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
				pass = false;
			} else if (!DataValidator.isPassword(request.getParameter("password"))) {
				request.setAttribute("password", "Password contain 8 letters with alpha-numeric & special Character");
				pass = false;
			} else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", "New password and Confirm password must be same!!");
				pass = false;
			}

		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "RoleName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		log.debug("UserCtl Method validate Ended");

		return pass;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println(" uctl Base bean P bean");
		log.debug("UserCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
	

		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		System.out.println("dob" + bean.getDob());

		populateDTO(bean, request);

		log.debug("UserCtl Method populatebean Ended");

		return bean;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserCtl Method doGet Started");
		System.out.println("u ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			UserBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("UserCtl Method doGet Ended");
		ServletUtility.forward(getView(), request, response);
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		log.debug("UserCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);

		System.out.println(">>>><<<<>><<><<><>**********" + id + op);

		UserModel model = new UserModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			System.out.println(" U ctl DoPost 11111111");

			try {
				if (id > 0) {
 
					// System.out.println("hi i am in dopost update");
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					ServletUtility.setSuccessMessage("User is successfully Updated", request);

				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);
					// bean.setId(pk);
					// ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("User is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);
					bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage("User is successfully saved", request);
				 */

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" U ctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" U ctl D p 5555555");

			UserBean bean = (UserBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}
		log.debug("UserCtl Method doPostEnded");
		
		ServletUtility.forward(getView(), request, response);


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}
