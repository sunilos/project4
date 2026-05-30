package com.sunilos.p4.rest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.model.RoleModel;

/**
 * REST controller for Role CRUD operations.
 * <p>
 * Mapped to {@code /rest/role/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/role?id=1}      — get a role by id</li>
 * <li>{@code GET    /rest/role}            — get all roles</li>
 * <li>{@code POST   /rest/role/add}        — add a new role</li>
 * <li>{@code POST   /rest/role/search}     — search roles by criteria</li>
 * <li>{@code PUT    /rest/role/update}     — update an existing role</li>
 * <li>{@code DELETE /rest/role/delete/1}   — delete a role by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/role/*" })
public class RoleRestCtl extends BaseRestController<RoleBean, RoleModel> {

	/**
	 * Maps JSON fields to a {@link RoleBean}, delegating base fields to
	 * {@link BaseRestController#jsonToBean} before adding role-specific fields.
	 *
	 * @param jsonNode the parsed JSON request body
	 * @param bean     the target bean to populate
	 * @return the populated {@link RoleBean}
	 */
	@Override
	public RoleBean jsonToBean(JsonNode jsonNode, RoleBean bean) {
		super.jsonToBean(jsonNode, bean);
		bean.setName(getJsonValue(jsonNode, "name"));
		bean.setDescription(getJsonValue(jsonNode, "description"));
		return bean;
	}

	/**
	 * No preload data is required for roles; this implementation is intentionally empty.
	 *
	 * @param request  the HTTP request
	 * @param response the HTTP response
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * Returns a new {@link RoleModel} instance; override in tests to inject a mock.
	 *
	 * @return new {@link RoleModel}
	 */
	@Override
	protected RoleModel getModel() {
		return new RoleModel();
	}

	/**
	 * Returns a new {@link RoleBean} instance for each request.
	 *
	 * @return new {@link RoleBean}
	 */
	@Override
	public RoleBean getBean() {
		return new RoleBean();
	}

}
