package com.sunilos.p4.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.BaseBean;
import com.sunilos.p4.model.BaseModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;
import com.sunilos.p4.util.ServletUtility;

/**
 * Base list controller class is extended by all the list pages in the
 * application. It contains search and pagination operation of a list page.
 * 
 * इस बेस लिस्ट कंट्रोलर क्लास को एप्लीकेशन के सारे लिस्ट पेजेज इन्हेरिट करते
 * है। एसकेअंदर किसी भी लिस्ट पेज के सर्च एवं पेजिनेशन के लॉजिक लिखे गए है
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

public abstract class BaseListCtl<B extends BaseBean, M extends BaseModel> extends BaseCtl<B, M> {

	private static Logger log = Logger.getLogger(BaseListCtl.class);

	/**
	 * It does call doPost method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * It contains search and pagination operations os a list page.
	 * 
	 * एसकेअंदर लिस्ट पेज के सर्च एवं पेजिनेशन के लॉजिक लिखे गए है
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("List toPost started");

		// Get the operation
		// ऑपरेशन पैरामीटर रिक्वेस्ट ऑब्जेक्ट से निकाले
		String op = DataUtility.getString(request.getParameter("operation"));

		// Create model
		M model = getModel();

		// Delete selected records
		if (OP_DELETE.equals(op)) {
			String[] ids = request.getParameterValues("ids");
			if (ids != null) {
				for (String id : ids) {
					model.delete(DataUtility.getInt(id));
				}
			}
		}

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

		// Calculate page number
		if (DataValidator.isNotNull(op)) {

			switch (op) {
			case OP_SEARCH:
				pageNo = 1;
				break;
			case OP_NEXT:
				pageNo++;
				break;
			case OP_PREVIOUS:
				if (pageNo > 1)
					pageNo--;
				break;
			}
		}

		// Search and get the list from model object
		// मॉडल ऑब्जेक्ट से सर्च करने के बाद लिस्ट प्राप्त करे
		List<B> list = model.search(bean, pageNo, pageSize);

		// Store list into request object
		// लिस्ट को रिक्वेस्ट ऑब्जेक्ट में स्टोर कर।
		ServletUtility.setList(list, request);

		// If list is empty then set error message
		// यदि लिस्ट खाली है तो एरर मैसेज सेट करे
		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}

		// Set page number and page size in the request object
		// पेज नंबर एवं पेज साइज को रिक्वेस्ट ऑब्जेक्ट में सेट करे
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);

		// Forward to view page
		// व्यू पेज पर फॉरवर्ड करे
		ServletUtility.forwardPage(getView(op), request, response);

		log.debug("List do post finish");

	}

}
