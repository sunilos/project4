package com.sunilos.p4.rest;

import javax.servlet.annotation.WebServlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.model.UserModel;

@WebServlet(urlPatterns = { "/rest/UserRestCtl", "/rest/UserRestCtl/*", })
public class UserRestCtl extends BaseRestController<UserBean, UserModel> {

	@Override
	public UserBean jsonToBean(JsonNode jsonNode, UserBean bean) {
		bean = super.jsonToBean(jsonNode, bean);
		bean.setFirstName(jsonNode.get("firstName").asText());
		bean.setLastName(jsonNode.get("lastName").asText());
		bean.setLogin(jsonNode.get("login").asText());
		bean.setPassword(jsonNode.get("password").asText());
		bean.setMobileNo(jsonNode.get("mobileNo").asText());
		bean.setGender(jsonNode.get("gender").asText());
		return bean;
	}

	@Override
	protected UserModel getModel() {
		return new UserModel();
	}

	@Override
	public UserBean getBean() {
		return new UserBean();
	}

}
