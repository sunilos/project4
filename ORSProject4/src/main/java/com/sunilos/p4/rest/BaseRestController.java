package com.sunilos.p4.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunilos.p4.bean.BaseBean;
import com.sunilos.p4.bean.ORSResponse;
import com.sunilos.p4.model.BaseModel;
import com.sunilos.p4.util.DataUtility;

/**
 * 
 * 
 * URLs
 * 
 ** HTTP GET rest/userctl/get/1
 * 
 ** HTTP GET rest/userctl/delete/1
 * 
 ** HTTP POST rest/userctl/save
 * 
 ** HTTP POST rest/userctl/add
 * 
 ** HTTP POST rest/userctl/update
 * 
 ** HTTP POST rest/userctl/search
 * 
 * @author Sunil Sahu
 *
 * @param <B>
 * @param <M>
 */
public abstract class BaseRestController<B extends BaseBean, M extends BaseModel> extends HttpServlet {

	protected abstract M getModel();

	protected void sendResponse(ORSResponse res, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(res);
			out.print(json);
			out.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parse the url http://localhost:8080/UserCtl/get/{id}
		String servPath = request.getServletPath();
		String url = request.getRequestURI();
		int startIndex = url.indexOf(servPath);
		String urlPart = url.substring(startIndex + servPath.length() + 1);
		String[] urlparts = urlPart.split("/");

		System.out.println("Path -> " + request.getServletPath());
		System.out.println("URI -> " + request.getRequestURI());
		System.out.println("URL -> " + request.getRequestURL());
		System.out.println("urlPart -> " + urlPart);
		System.out.println("urlparts -> " + urlparts);

		// GET method must have data
		if (urlparts.length == 1 && request.getMethod().toLowerCase().equals("get")) {
			ORSResponse res = new ORSResponse(false, "invalid request");
			sendResponse(res, request, response);
			return;
		}

		// Keep url parts in request scope
		request.setAttribute("operation", urlparts[0]);

		if (urlparts.length == 2) {
			request.setAttribute("data", urlparts[1]);
		}
		super.service(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ORSResponse res = new ORSResponse(true);

		String operation = (String) request.getAttribute("operation");
		// parse pk froma url
		long pk = DataUtility.getLong((String) request.getAttribute("data"));

		M model = getModel();
		B bean = (B) model.findByPK(pk);

		if (bean == null) {
			res = new ORSResponse(false, "Record does not exist");
			sendResponse(res, request, response);
			return;
		}

		if ("get".equals(operation)) {
			res = new ORSResponse(true);
			res.addData(bean);
		} else if ("delete".equals(operation)) {
			model.delete(bean);
			res = new ORSResponse(true, "Record is successfully deleted");
			res.addData(bean);
		} else {
			res = new ORSResponse(false, operation + " is invalid");
		}
		sendResponse(res, request, response);
	}

	/**
	 * performs add, update, search, and save (add/update)operations
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ORSResponse res = new ORSResponse(true);

		String operation = (String) request.getAttribute("operation");

		M model = getModel();

		// Obtain the input stream from the request
		InputStream inputStream = request.getInputStream();
		// Initialize ObjectMapper (Jackson)
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Parse the JSON data
			JsonNode jsonNode = objectMapper.readTree(inputStream);
			B bean = jsonToBean(jsonNode, getBean());

			if ("save".equals(operation)) {
				if (bean.getId() > 0) {
					model.update(bean);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);
				}
			} else if ("add".equals(operation)) {
				long pk = model.add(bean);
				bean = (B) model.findByPK(pk);
			} else if ("update".equals(operation)) {
				model.update(bean);
				bean = (B) model.findByPK(bean.getId());
			} else if ("search".equals(operation)) {
				List l = model.search(bean);
				res = new ORSResponse(true);
				res.addData(l);
				sendResponse(res, request, response);
				return;
			}
			res = new ORSResponse(true, "Data is saved");
			res.addData(bean);
			sendResponse(res, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			res = new ORSResponse(false, e.getMessage());
			sendResponse(res, request, response);
		}
	}

	/**
	 * Data is populated from JSON to Bean
	 * 
	 * @param jsonNode
	 * @param bean
	 * @return
	 */
	public B jsonToBean(JsonNode jsonNode, B bean) {
		bean.setId(DataUtility.getLong(getJsonValue(jsonNode, "id")));
		bean.setCreatedDatetime(new Timestamp(DataUtility.getLong(getJsonValue(jsonNode, "createdDatetime"))));
		bean.setModifiedDatetime(new Timestamp(DataUtility.getLong(getJsonValue(jsonNode, "modifiedDatetime"))));
		bean.setModifiedBy(getJsonValue(jsonNode, "modifiedBy"));
		bean.setCreatedBy(getJsonValue(jsonNode, "createdBy"));
		return bean;
	}

	public abstract B getBean();

	public String getJsonValue(JsonNode jsonNode, String key) {
		JsonNode val = null;
		val = jsonNode.get(key);
		if (val != null) {
			return val.asText();
		} else {
			return null;
		}
	}

	public long getJsonLongValue(JsonNode jsonNode, String key) {
		JsonNode val = null;
		val = jsonNode.get(key);
		if (val != null) {
			return val.asLong();
		} else {
			return 0;
		}
	}

}
