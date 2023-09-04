package com.sunilos.p4.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.BaseBean;
import com.sunilos.p4.ctl.BaseCtl;
import com.sunilos.p4.ctl.ORSView;

/**
 * This is a utility class that provides basic servlet operations like forward,
 * redirect, generic exception handling, access success and error messages, set
 * and get default Bean and List objects, manage pagination parameters
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

public class ServletUtility {

	private static Logger log = Logger.getLogger(ServletUtility.class);

	/**
	 * Forward to given JSP/Servlet
	 * 
	 * @param page
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	public static void forwardPage(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("Forwarding to page : " + page);
		if (page.startsWith("/")) {
			page = page.substring(1);
		}
		request.setAttribute("p", page);
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * Redirect to given JSP/Servlet
	 * 
	 * @param page
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.sendRedirect(page);
	}

	/**
	 * Handle an exception and redirect to the application Error Handler Page
	 * 
	 * @param e
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		e.printStackTrace();
		log.error(e);
		request.setAttribute("exception", e);
		response.sendRedirect(ORSView.ERROR_CTL);
	}

	/**
	 * Gets a message from the request object
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getMessage(String key, HttpServletRequest request) {
		String val = (String) request.getAttribute(key);
		if (DataValidator.isNull(val))
			val = "";
		return val;
	}

	/**
	 * Sets a message in the request object
	 * 
	 * @param property
	 * @param request
	 * @return
	 */
	public static void setMessage(String key, String msg, HttpServletRequest request) {
		if (DataValidator.isNotNull(key) && DataValidator.isNotNull(msg)) {
			request.setAttribute(key, msg);
		}
	}

	/**
	 * Sets error message in the request object
	 * 
	 * @param msg
	 * @param request
	 */
	public static void setErrorMessage(String msg, HttpServletRequest request) {
		setMessage(BaseCtl.MSG_ERROR, msg, request);
	}

	/**
	 * Gets error message from request
	 * 
	 * @param request
	 * @return
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String msg = getMessage(BaseCtl.MSG_ERROR, request);
		if (DataValidator.isNull(msg)) {
			msg = request.getParameter(BaseCtl.MSG_ERROR);
		}
		return DataUtility.getStringData(msg);
	}

	/**
	 * Gets error message for given key
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getErrorMessage(String key, HttpServletRequest request) {
		return getMessage(key, request);
	}

	/**
	 * Sets success message to the request
	 * 
	 * @param msg
	 * @param request
	 */
	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		setMessage(BaseCtl.MSG_SUCCESS, msg, request);
	}

	/**
	 * Gets success message from request
	 * 
	 * @param request
	 * @return
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		String msg = getMessage(BaseCtl.MSG_SUCCESS, request);
		if (DataValidator.isNull(msg)) {
			msg = request.getParameter(BaseCtl.MSG_SUCCESS);
		}
		return DataUtility.getStringData(msg);
	}

	/**
	 * Sets default Bean to request
	 * 
	 * @param bean
	 * @param request
	 */
	public static void setBean(BaseBean bean, HttpServletRequest request) {
		request.setAttribute("bean", bean);
	}

	/**
	 * Gets default bean from request
	 * 
	 * @param request
	 * @return
	 */

	public static BaseBean getBean(HttpServletRequest request) {
		return (BaseBean) request.getAttribute("bean");
	}

	/**
	 * Get request parameter to display. If value is null then return empty string
	 * 
	 * @param property
	 * @param request
	 * @return
	 */

	public static String getParameter(String property, HttpServletRequest request) {
		String val = request.getParameter(property);
		if (DataValidator.isNull(val))
			val = "";
		return val;
	}

	/**
	 * Sets default List to request
	 * 
	 * @param list
	 * @param request
	 */
	public static void setList(List list, HttpServletRequest request) {
		request.setAttribute("list", list);
	}

	/**
	 * Gets default list from request
	 * 
	 * @param request
	 * @return
	 */
	public static List getList(HttpServletRequest request) {
		return (List) request.getAttribute("list");
	}

	/**
	 * Sets Page Number for List pages
	 * 
	 * @param pageNo
	 * @param request
	 */
	public static void setPageNo(int pageNo, HttpServletRequest request) {
		request.setAttribute("pageNo", pageNo);
	}

	/**
	 * Gets Page Number for List pages
	 * 
	 * @param request
	 * @return
	 */
	public static int getPageNo(HttpServletRequest request) {
		return (int) request.getAttribute("pageNo");
	}

	/**
	 * Sets Page Size for list pages
	 * 
	 * @param pageSize
	 * @param request
	 */
	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	/**
	 * Gets Page Size for List pages
	 * 
	 * @param request
	 * @return
	 */
	public static int getPageSize(HttpServletRequest request) {
		return (int) request.getAttribute("pageSize");
	}

	public static void main(String[] args) {
		Integer val = null;
		int i = val;
	}
}
