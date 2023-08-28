package com.sunilos.p4.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.BaseBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.model.BaseModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.PropertyReader;
import com.sunilos.p4.util.ServletUtility;

/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

public abstract class BaseListCtl<B extends BaseBean, M extends BaseModel> extends BaseCtl<B, M> {

	private static Logger log = Logger.getLogger(BaseListCtl.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CollegeListCtl doGet Start");

		// Get page number from request
		// पेज नंबर को रिक्वेस्ट ऑब्जेक्ट से निकाले
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		// Get pagesize from request
		// पेज साइज को रिक्वेस्ट ऑब्जेक्ट से निकाले
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		// If page number is 0 then make it first page
		// यदि पेज नंबर 0 नहीं है तो पेज नंबर 1 माने
		pageNo = (pageNo == 0) ? 1 : pageNo;

		// if page size is zero then get it from properties file
		// यदि पेज साइज 0 हो तो प्रॉपर्टी फाइल से निकाले
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		// Get the data from request object and set into bean object
		// यूजर डाटा रिक्वेस्ट ऑब्जेक्ट में से निकाल कर बिन ऑब्जेक्ट में सेट करे
		BaseBean bean = populateBean(request);

		// Get the operation
		// ऑपरेशन पैरामीटर रिक्वेस्ट ऑब्जेक्ट से निकाले
		String op = DataUtility.getString(request.getParameter("operation"));

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			}

			// Search objects with the help of model object on the basis of bean attribute
			// values
			List<B> list = getModel().search(bean, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(op), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("CollegeListCtl doGet End");

	}

}
