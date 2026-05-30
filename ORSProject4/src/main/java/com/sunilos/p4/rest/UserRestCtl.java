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

/**
 * REST controller for User CRUD operations.
 * <p>
 * Mapped to {@code /rest/user/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/user?id=1}      — get a user by id</li>
 * <li>{@code GET    /rest/user}            — get all users</li>
 * <li>{@code GET    /rest/user/preload}    — get preload data (role list)</li>
 * <li>{@code POST   /rest/user/add}        — add a new user</li>
 * <li>{@code POST   /rest/user/search}     — search users by criteria</li>
 * <li>{@code PUT    /rest/user/update}     — update an existing user</li>
 * <li>{@code DELETE /rest/user/delete/1}   — delete a user by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/user/*" })
public class UserRestCtl extends BaseRestController<UserBean, UserModel> {

	/**
	 * Maps JSON fields to a {@link UserBean}, delegating base fields to
	 * {@link BaseRestController#jsonToBean} before adding user-specific fields.
	 * <p>
	 * Note: {@code dob} is omitted — date parsing from JSON is not yet implemented.
	 *
	 * @param jsonNode the parsed JSON request body
	 * @param bean     the target bean to populate
	 * @return the populated {@link UserBean}
	 */
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

	/**
	 * Handles {@code GET /rest/user/preload}.
	 * Returns the full list of roles, used to populate the role dropdown
	 * on the user form.
	 *
	 * @param request  the HTTP request
	 * @param response the HTTP response; returns {@code { "preload": { "roleList": [...] } }}
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if writing the response fails
	 */
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

	/**
	 * Returns a new {@link UserModel} instance; override in tests to inject a mock.
	 *
	 * @return new {@link UserModel}
	 */
	@Override
	protected UserModel getModel() {
		return new UserModel();
	}

	/**
	 * Returns a new {@link UserBean} instance for each request.
	 *
	 * @return new {@link UserBean}
	 */
	@Override
	public UserBean getBean() {
		return new UserBean();
	}

}
