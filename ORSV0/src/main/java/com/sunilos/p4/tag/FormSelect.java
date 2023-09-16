package com.sunilos.p4.tag;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.HTMLUtility;

/**
 * Tag creates the HTML SELECT elements
 *
 * <ors:formSelect name="roleId" value="1" list="roleList" />
 * 
 * @author Sunil Sahu
 *
 */
public class FormSelect extends TagSupport {

	/**
	 * Name of the SELECT element
	 */
	private String name = null;

	/**
	 * Value of the SELECT element
	 */
	private String value = null;

	/**
	 * Name of the List/Map stored in requet scope
	 */
	private String list = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	@Override
	public int doStartTag() throws JspException {
		try {

			HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
			Object o = req.getAttribute(list);

			String htmlList = null;

			if (o instanceof HashMap) {
				htmlList = HTMLUtility.getList(name, value, (HashMap) o);
			} else if (o instanceof List) {
				htmlList = HTMLUtility.getList(name, value, (List) o);
			}

			if (DataValidator.isNotNull(htmlList)) {
				JspWriter out = pageContext.getOut();
				out.println(htmlList);
			}

		} catch (Exception e) {
			throw new JspException(e);
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

}
