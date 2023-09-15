package com.sunilos.p4.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sunilos.p4.ctl.BaseCtl;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.ServletUtility;

public class FormMessageTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		try {

			HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
			String err = ServletUtility.getMessage(BaseCtl.HAS_ERROR, req);
			String msg = ServletUtility.getMessage(BaseCtl.MESSAGE, req);

			if (DataValidator.isNull(msg)) {
				msg = "no message";
			}

			// Get the JspWriter to write content to the JSP
			JspWriter out = pageContext.getOut();
			if (msg != null) {
				if ("true".equals(err)) {
					out.println("<p class=\"error-message\">" + msg + "</p>");
				} else {
					out.println("<p class=\"success-message\">" + msg + "</p>");
				}
			}
		} catch (Exception e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}
}
