package com.sunilos.p4.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.ORSResponse;
import com.sunilos.p4.bean.StudentBean;
import com.sunilos.p4.model.CollegeModel;
import com.sunilos.p4.model.StudentModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for Student CRUD operations.
 * <p>
 * Mapped to {@code /rest/student/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/student?id=1}      — get a student by id</li>
 * <li>{@code GET    /rest/student}            — get all students</li>
 * <li>{@code GET    /rest/student/preload}    — get preload data (college list)</li>
 * <li>{@code POST   /rest/student/add}        — add a new student</li>
 * <li>{@code POST   /rest/student/search}     — search students by criteria</li>
 * <li>{@code PUT    /rest/student/update}     — update an existing student</li>
 * <li>{@code DELETE /rest/student/delete/1}   — delete a student by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/student/*" })
public class StudentRestCtl extends BaseRestController<StudentBean, StudentModel> {

	/**
	 * Maps JSON fields to a {@link StudentBean}, delegating base fields to
	 * {@link BaseRestController#jsonToBean} before adding student-specific fields.
	 * <p>
	 * Note: {@code dob} is omitted — date parsing from JSON is not yet implemented.
	 *
	 * @param jsonNode the parsed JSON request body
	 * @param bean     the target bean to populate
	 * @return the populated {@link StudentBean}
	 */
	@Override
	public StudentBean jsonToBean(JsonNode jsonNode, StudentBean bean) {
		super.jsonToBean(jsonNode, bean);
		bean.setCollegeId(getJsonLongValue(jsonNode, "collegeId"));
		bean.setCollegeName(getJsonValue(jsonNode, "collegeName"));
		bean.setEmail(getJsonValue(jsonNode, "email"));
		bean.setFirstName(getJsonValue(jsonNode, "firstName"));
		bean.setLastName(getJsonValue(jsonNode, "lastName"));
		bean.setMobileNo(getJsonValue(jsonNode, "mobileNo"));
		// dob omitted — date parsing from JSON not yet implemented
		return bean;
	}

	/**
	 * Handles {@code GET /rest/student/preload}.
	 * Returns the full list of colleges, used to populate the college dropdown
	 * on the student form.
	 *
	 * @param request  the HTTP request
	 * @param response the HTTP response; returns {@code { "preload": { "collegeList": [...] } }}
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if writing the response fails
	 */
	@Override
	protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CollegeModel model = new CollegeModel();
		List<?> l = model.list();
		HashMap<String, Object> map = new HashMap<>();
		map.put("collegeList", l);

		ORSResponse res = new ORSResponse(true);
		res.addResult("preload", map);
		sendResponse(res, request, response);
	}

	/**
	 * Returns a new {@link StudentModel} instance; override in tests to inject a mock.
	 *
	 * @return new {@link StudentModel}
	 */
	@Override
	protected StudentModel getModel() {
		return new StudentModel();
	}

	/**
	 * Returns a new {@link StudentBean} instance for each request.
	 *
	 * @return new {@link StudentBean}
	 */
	@Override
	public StudentBean getBean() {
		return new StudentBean();
	}

}
