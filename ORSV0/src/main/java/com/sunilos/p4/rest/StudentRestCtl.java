package com.sunilos.p4.rest;

import javax.servlet.annotation.WebServlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.StudentBean;
import com.sunilos.p4.model.StudentModel;

@WebServlet(urlPatterns = { "/rest/studentctl/*" })
public class StudentRestCtl extends BaseRestController<StudentBean, StudentModel> {

	@Override
	public StudentBean jsonToBean(JsonNode jsonNode, StudentBean bean) {
		bean = super.jsonToBean(jsonNode, bean);
		bean.setCollegeId(getJsonLongValue(jsonNode, "id"));
		bean.setCollegeName(getJsonValue(jsonNode, "collegeName"));
		// bean.setDob(getJsonValue(jsonNode, "dob"));
		bean.setEmail(getJsonValue(jsonNode, "email"));
		bean.setFirstName(getJsonValue(jsonNode, "firstName"));
		bean.setLastName(getJsonValue(jsonNode, "lastName"));
		bean.setMobileNo(getJsonValue(jsonNode, "mobileNo"));
		return bean;

	}

	@Override
	protected StudentModel getModel() {
		return new StudentModel();
	}

	@Override
	public StudentBean getBean() {
		return new StudentBean();
	}

}
