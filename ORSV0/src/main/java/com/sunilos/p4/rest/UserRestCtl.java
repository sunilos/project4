package com.sunilos.p4.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunilos.p4.bean.ORSResponse;
import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.model.BaseModel;
import com.sunilos.p4.model.UserModel;
import com.sunilos.p4.util.DataUtility;

@WebServlet(urlPatterns = { "/rest/UserRestCtl", "/rest/UserRestCtl/*", })
public class UserRestCtl extends BaseRestController {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String servPath = request.getServletPath();
		String url = request.getRequestURI();
		int startIndex = url.indexOf(servPath);

		String urlPart = url.substring(startIndex + servPath.length() + 1);
		String[] urlparts = urlPart.split("/");

		System.out.println("Path -> " + request.getServletPath());
		System.out.println("URI -> " + request.getRequestURI());
		System.out.println("URL -> " + request.getRequestURL());
		System.out.println("urlPart -> " + urlPart);

		String id = DataUtility.getString(request.getParameter("id"));
		UserBean bean = (UserBean) getModel().findByPK(DataUtility.getLong(id));
		ORSResponse res = new ORSResponse();
		if (bean != null) {
			res.addData(bean);
		} else {
			res = new ORSResponse(false, "Record does not exist");
		}

		response.setContentType("application/json");

		PrintWriter out = response.getWriter();

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(res);
		out.print(json);
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, res);
	}

	@Override
	protected BaseModel getModel() {
		return new UserModel();
	}

}
