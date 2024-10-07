package com.sunilos.pro4.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
* The Class ChangePasswordCtl.
* 
* @author Sunil OS
*/
@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl{

	private static final long serialVersionUID = 1L;

	/** The Constant OP_CHANGE_MY_PROFILE. */
	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	/** The log. */
	private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ChangePasswordCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {

			return pass;
		}
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		}
	
		/*else if (!DataValidator.isPassword(request.getParameter("oldPassword"))) {
			  request.setAttribute("oldPassword","Password should contain 8 letter with alpha-numeric and special Character");
			  pass = false; 
			  }
			 */
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		}
		else if(request.getParameter("oldPassword").equals(request.getParameter("newPassword"))){
		request.setAttribute("newPassword", "Old password and New password should not be same!!");
		pass = false;
		}
		
		else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			  request.setAttribute("newPassword","Password should contain 8 letter with alpha-numeric,capital latter and special Character");
			  pass = false; 
			  }
			 
		
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
	
		else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
			  request.setAttribute("confirmPassword","Password should contain 8 letter with alpha-numeric,capital latter and special Character");
			  pass = false; 
			  }
		
		else if(!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))){
			request.setAttribute("confirmPassword", "New password and Confirm password must be same!!");
			pass = false;
			}
			 
		
	/*	if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage("New and confirm passwords should be matched", request);

			pass = false;
		}*/
        
		/*if (request.getParameter("oldPassword").equals(request.getParameter("newPassword")))
				{
			ServletUtility.setErrorMessage("Old and NewPasswords should not be same", request);

			pass = false;
		}*/

		
		log.debug("ChangePasswordCtl Method validate Ended");

		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("ChangePasswordCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		populateDTO(bean, request);

		log.debug("ChangePasswordCtl Method populatebean Ended");

		return bean;

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
        
		log.debug("ChangePasswordCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model      
		UserModel model = new UserModel();

		UserBean bean = (UserBean) populateBean(request);

		UserBean UserBean = (UserBean) session.getAttribute("user");

		String newPassword = (String) request.getParameter("newPassword");

		long id = UserBean.getId();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
				if (flag == true) {
					bean = model.findByLogin(UserBean.getLogin());
					session.setAttribute("user", bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Password has been changed Successfully.", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}
		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;

		}
		ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
		log.debug("ChangePasswordCtl Method doGet Ended");
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}
	
}
