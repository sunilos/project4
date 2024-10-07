package com.sunilos.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.MalformedLinkException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.pro4.bean.BaseBean;
import com.sunilos.pro4.bean.MarksheetBean;
import com.sunilos.pro4.exception.ApplicationException;
import com.sunilos.pro4.model.MarksheetModel;
import com.sunilos.pro4.util.DataUtility;
import com.sunilos.pro4.util.PropertyReader;
import com.sunilos.pro4.util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
* Marksheet List functionality Controller. Performs operation for list, search
* and delete operations of Marksheet
* 
* @author SunilOS
* @version 1.0
* @Copyright (c) SunilOS
*/

/**
* Servlet implementation class MarksheetlistCtl
*  @author  Sunil OS
*/
@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })
public class MarksheetListCtl extends BaseCtl {

	/** The log. */
	private static Logger log = Logger.getLogger(MarksheetListCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		MarksheetModel model= new  MarksheetModel();
		try {
			List list = model.list(0, 0);
			request.setAttribute("rollNo", list);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("rollNo123")));
	    bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setTotal(DataUtility.getInt(request.getParameter("total")));

		return bean;
	}

	/**
	 * ContainsDisplaylogics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		MarksheetBean bean = (MarksheetBean) populateBean(request);
		String[] ids = request.getParameterValues("ids");
		List list;
		MarksheetModel model = new MarksheetModel();
		try {
			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			log.debug("MarksheetListCtl doGet End");

		} catch (ApplicationException e) {
			e.printStackTrace();
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Contains Submit logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetListCtl doPost Start");

		List list = null;

		List nextList = null;
		String op = DataUtility.getString(request.getParameter("operation"));

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		MarksheetBean bean = (MarksheetBean) populateBean(request);

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");

		MarksheetModel model = new MarksheetModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		}

		else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				MarksheetBean deletebean = new MarksheetBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
					}
					ServletUtility.setSuccessMessage(" Marksheet Data Successfully Deleted ", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setBean(bean, request);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
		ServletUtility.setList(list, request);
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

		log.debug("Marksheet List Ctl do post End");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
