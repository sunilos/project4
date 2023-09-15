package com.sunilos.p4.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sunilos.p4.util.MessageSource;

public class MessageTag extends TagSupport {

	private MessageSource ms = MessageSource.getInstance();

	private String key = null;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			String val = ms.get(key);
			// Get the JspWriter to write content to the JSP
			JspWriter out = pageContext.getOut();
			out.write(val);
		} catch (Exception e) {
			throw new JspException(e);
		}
		// Your custom tag logic goes here
		return SKIP_BODY;
	}
}
