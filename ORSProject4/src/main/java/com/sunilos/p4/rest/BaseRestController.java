package com.sunilos.p4.rest;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	protected abstract M getModel();

	protected void sendResponse(ORSResponse res, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(OBJECT_MAPPER.writeValueAsString(res));
			out.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Pre-processes every request by extracting path segments from
	 * {@link HttpServletRequest#getPathInfo()} and storing them as request
	 * attributes before delegating to the appropriate {@code doXxx} method.
	 * <p>
	 * For a URL like {@code DELETE rest/userctl/delete/1}:
	 * <ul>
	 * <li>{@code operation} attribute → {@code "delete"}</li>
	 * <li>{@code data} attribute → {@code "1"}</li>
	 * </ul>
	 *
	 * @param request  the incoming HTTP request
	 * @param response the HTTP response
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if writing the response fails
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		System.out.println("pathInfo" + pathInfo);
		if (pathInfo != null && pathInfo.length() > 1) {
			String[] parts = pathInfo.substring(1).split("/");
			request.setAttribute("operation", parts[0]);
			System.out.println("operation" + parts[0]);
			if (parts.length >= 2) {
				request.setAttribute("data", parts[1]);
				System.out.println("data" + parts[1]);
			}

		}
		super.service(request, response);
	}

	/**
	 * Handles HTTP GET requests.
	 * <p>
	 * If request parameter {@code id > 0}, returns the matching record.
	 * Otherwise returns all records.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>{@code GET rest/userctl?id=1} — returns single record</li>
	 * <li>{@code GET rest/userctl} — returns all records</li>
	 * </ul>
	 *
	 * @param request  the HTTP request; reads optional {@code id} parameter
	 * @param response the HTTP response; returns matching record(s) as JSON
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if writing the response fails
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String operation = (String) request.getAttribute("operation");
		if ("preload".equalsIgnoreCase(operation)) {
			doGetPreload(request, response);
			return;
		}

		ORSResponse res;
		M model = getModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		try {
			if (id > 0) {
				B bean = (B) model.findByPK(id);
				if (bean == null) {
					res = new ORSResponse(false, "Record not found for id: " + id);
				} else {
					res = new ORSResponse(true);
					res.addData(bean);
				}
			} else {
				List<B> list = model.list();
				res = new ORSResponse(true);
				res.addData(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res = new ORSResponse(false, e.getMessage());
		}

		sendResponse(res, request, response);
	}

	protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ORSResponse res = new ORSResponse(false, "Preload not implemented");
		sendResponse(res, request, response);
	}

	protected void doPostSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ORSResponse res;
		M model = getModel();

		try {
			JsonNode jsonNode = OBJECT_MAPPER.readTree(request.getInputStream());
			B bean = jsonToBean(jsonNode, getBean());

			List<B> list = model.search(bean);
			res = new ORSResponse(true, "Records found");
			res.addData(list);
			sendResponse(res, request, response);

		} catch (Exception e) {
			e.printStackTrace();
			res = new ORSResponse(false, e.getMessage());
			sendResponse(res, request, response);
		}
	}

	/**
	 * Handles HTTP POST requests for add, update, save (upsert), and search
	 * operations.
	 * <p>
	 * Operation is determined by the first URL segment:
	 * <ul>
	 * <li>{@code add} — inserts a new record; returns the persisted bean</li>
	 * <li>{@code update} — updates an existing record; returns the refreshed
	 * bean</li>
	 * <li>{@code save} — inserts if {@code id == 0}, updates otherwise; returns the
	 * refreshed bean</li>
	 * <li>{@code search} — returns a list of matching records</li>
	 * </ul>
	 *
	 * @param request  the HTTP request carrying the JSON bean in its body
	 * @param response the HTTP response; returns the result as JSON
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if reading the request body or writing the response
	 *                          fails
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String operation = (String) request.getAttribute("operation");
		if ("search".equalsIgnoreCase(operation)) {
			doPostSearch(request, response);
			return;
		}

		ORSResponse res;
		M model = getModel();

		try {
			JsonNode jsonNode = OBJECT_MAPPER.readTree(request.getInputStream());
			B bean = jsonToBean(jsonNode, getBean());

			long pk = model.add(bean);
			bean = (B) model.findByPK(pk);
			res = new ORSResponse(true, "Record is successfully added");
			res.addData(bean);
			sendResponse(res, request, response);

		} catch (Exception e) {
			e.printStackTrace();
			res = new ORSResponse(false, e.getMessage());
			sendResponse(res, request, response);
		}
	}

	/**
	 * Handles HTTP PUT requests to update an existing record.
	 * <p>
	 * Expects a JSON body representing the bean with a valid {@code id} field.
	 * Fetches the persisted bean after the update and returns it in the response.
	 * <p>
	 * Example: {@code PUT rest/userctl/update} with JSON body containing the bean.
	 *
	 * @param request  the HTTP request carrying the JSON bean in its body
	 * @param response the HTTP response; returns the updated bean as JSON
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if reading the request body or writing the response
	 *                          fails
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ORSResponse res;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
			B bean = jsonToBean(jsonNode, getBean());

			if (bean.getId() <= 0) {
				res = new ORSResponse(false, "Invalid or missing id — cannot update record");
				sendResponse(res, request, response);
				return;
			}

			M model = getModel();

			if (model.findByPK(bean.getId()) == null) {
				res = new ORSResponse(false, "Record not found for id: " + bean.getId());
				sendResponse(res, request, response);
				return;
			}

			model.update(bean);
			B updated = (B) model.findByPK(bean.getId());

			res = new ORSResponse(true, "Record is successfully updated");
			res.addData(updated);
			sendResponse(res, request, response);

		} catch (Exception e) {
			e.printStackTrace();
			res = new ORSResponse(false, e.getMessage());
			sendResponse(res, request, response);
		}
	}

	/**
	 * Handles HTTP DELETE requests to delete a record by primary key.
	 * <p>
	 * The record ID is read from the {@code data} request attribute, which is
	 * populated by {@link #service} from the URL segment after the operation.
	 * <p>
	 * Example: {@code DELETE rest/userctl/delete/1}
	 *
	 * @param request  the HTTP request; reads {@code data} attribute for the record
	 *                 ID
	 * @param response the HTTP response; returns the deleted bean as JSON on
	 *                 success
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if writing the response fails
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ORSResponse res;

		try {
			long pk = DataUtility.getLong((String) request.getAttribute("data"));

			if (pk <= 0) {
				res = new ORSResponse(false, "Invalid or missing id — cannot delete record");
				sendResponse(res, request, response);
				return;
			}

			M model = getModel();
			B bean = (B) model.findByPK(pk);

			if (bean == null) {
				res = new ORSResponse(false, "Record not found for id: " + pk);
				sendResponse(res, request, response);
				return;
			}

			model.delete(bean);
			res = new ORSResponse(true, "Record is successfully deleted");
			res.addData(bean);
			sendResponse(res, request, response);

		} catch (Exception e) {
			e.printStackTrace();
			res = new ORSResponse(false, e.getMessage());
			sendResponse(res, request, response);
		}
	}

	/**
	 * Handles HTTP HEAD requests identically to GET but omits the response body.
	 * Useful for clients that need to check record existence or response metadata
	 * without transferring the payload.
	 */
	@Override
	protected void doHead(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		// Delegate to doGet; the servlet container suppresses the body for HEAD.
		doGet(request, response);
	}

	/**
	 * Handles HTTP OPTIONS requests by advertising the HTTP methods supported by
	 * this controller via the {@code Allow} response header.
	 */
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Allow", "GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Handles HTTP TRACE requests by echoing the incoming request headers back to
	 * the client as {@code message/http} content, which is the standard behaviour
	 * for diagnostic loop-back testing.
	 */
	@Override
	protected void doTrace(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("message/http");
		StringBuilder sb = new StringBuilder();
		sb.append("TRACE ").append(request.getRequestURI()).append(" ").append(request.getProtocol()).append("\r\n");
		java.util.Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			sb.append(name).append(": ").append(request.getHeader(name)).append("\r\n");
		}
		response.setContentLength(sb.length());
		response.getWriter().print(sb.toString());
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
