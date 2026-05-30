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
 * Mapped to {@code /rest/rolectl/*}. Supports:
 * <ul>
 * <li>{@code GET  /rest/rolectl?id=1} — get by id</li>
 * <li>{@code GET  /rest/rolectl} — get all</li>
 * <li>{@code POST /rest/rolectl/add} — add new role</li>
 * <li>{@code PUT  /rest/rolectl/update} — update role</li>
 * <li>{@code DELETE /rest/rolectl/delete/1} — delete by id</li>
 * </ul>
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/role/*" })
public class RoleRestCtl extends BaseRestController<RoleBean, RoleModel> {

	private static final RoleModel MODEL = new RoleModel();

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

	@Override
	protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * Returns the shared stateless {@link RoleModel} instance.
	 *
	 * @return singleton {@link RoleModel}
	 */
	@Override
	protected RoleModel getModel() {
		return MODEL;
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
