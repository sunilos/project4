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

@WebServlet(urlPatterns = { "/rest/student/*" })
public class StudentRestCtl extends BaseRestController<StudentBean, StudentModel> {

	private static final StudentModel MODEL = new StudentModel();

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

	@Override
	protected StudentModel getModel() {
		return MODEL;
	}

	@Override
	public StudentBean getBean() {
		return new StudentBean();
	}

}
