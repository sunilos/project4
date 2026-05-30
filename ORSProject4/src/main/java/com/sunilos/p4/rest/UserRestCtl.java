package com.sunilos.p4.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.ORSResponse;
import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.model.RoleModel;
import com.sunilos.p4.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = { "/rest/user/*" })
public class UserRestCtl extends BaseRestController<UserBean, UserModel> {

	private static final UserModel MODEL = new UserModel();

	@Override
	public UserBean jsonToBean(JsonNode jsonNode, UserBean bean) {
		super.jsonToBean(jsonNode, bean);
		bean.setRoleId(getJsonLongValue(jsonNode, "roleId"));
		bean.setFirstName(getJsonValue(jsonNode, "firstName"));
		bean.setLastName(getJsonValue(jsonNode, "lastName"));
		bean.setLogin(getJsonValue(jsonNode, "login"));
		bean.setPassword(getJsonValue(jsonNode, "password"));
		bean.setMobileNo(getJsonValue(jsonNode, "mobileNo"));
		bean.setGender(getJsonValue(jsonNode, "gender"));
		// dob omitted — date parsing from JSON not yet implemented
		return bean;
	}

	@Override
	protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RoleModel model = new RoleModel();
		List<?> l = model.list();
		HashMap<String, Object> map = new HashMap<>();
		map.put("roleList", l);

		ORSResponse res = new ORSResponse(true);
		res.addResult("preload", map);
		sendResponse(res, request, response);
	}

	@Override
	protected UserModel getModel() {
		return MODEL;
	}

	@Override
	public UserBean getBean() {
		return new UserBean();
	}

}
