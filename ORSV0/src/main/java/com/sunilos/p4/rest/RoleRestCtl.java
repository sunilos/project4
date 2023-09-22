package com.sunilos.p4.rest;

import javax.servlet.annotation.WebServlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.model.RoleModel;

@WebServlet(urlPatterns = { "/rest/rolectl/*" })
public class RoleRestCtl extends BaseRestController<RoleBean, RoleModel> {

	@Override
	public RoleBean jsonToBean(JsonNode jsonNode, RoleBean bean) {
		bean = super.jsonToBean(jsonNode, bean);
		bean.setName(getJsonValue(jsonNode, "name"));
		bean.setDescription(getJsonValue(jsonNode, "description"));
		return bean;

	}

	@Override
	protected RoleModel getModel() {
		return new RoleModel();
	}

	@Override
	public RoleBean getBean() {
		return new RoleBean();
	}

}
