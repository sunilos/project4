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
 * Abstract base REST controller that provides full CRUD and diagnostic HTTP
 * method support for all entity-specific subclasses.
 * <p>
 * Each concrete subclass maps to a resource-specific URL prefix via
 * {@code @WebServlet}. The table below uses {@code /rest/{resource}} as a
 * placeholder — replace {@code {resource}} with the actual mapping of the
 * subclass (e.g. {@code college}, {@code student}, {@code marksheet}, …).
 *
 * <table border="1" cellpadding="4">
 * <caption>Supported REST API endpoints</caption>
 * <tr><th>Method</th><th>URL</th><th>Description</th></tr>
 * <tr><td>GET</td>    <td>/rest/{resource}?id={id}</td>         <td>Get a single record by primary key</td></tr>
 * <tr><td>GET</td>    <td>/rest/{resource}</td>                 <td>Get all records</td></tr>
 * <tr><td>GET</td>    <td>/rest/{resource}/preload</td>         <td>Get preload data (e.g. dropdown lists)</td></tr>
 * <tr><td>POST</td>   <td>/rest/{resource}/add</td>             <td>Add a new record; body: JSON bean</td></tr>
 * <tr><td>POST</td>   <td>/rest/{resource}/search</td>          <td>Search records by criteria; body: JSON bean</td></tr>
 * <tr><td>PUT</td>    <td>/rest/{resource}/update</td>          <td>Update an existing record; body: JSON bean with id</td></tr>
 * <tr><td>DELETE</td> <td>/rest/{resource}/delete/{id}</td>     <td>Delete a record by primary key</td></tr>
 * <tr><td>HEAD</td>   <td>/rest/{resource}</td>                 <td>Same as GET but response body is omitted</td></tr>
 * <tr><td>OPTIONS</td><td>/rest/{resource}</td>                 <td>Returns supported HTTP methods in Allow header</td></tr>
 * <tr><td>TRACE</td>  <td>/rest/{resource}</td>                 <td>Echoes request headers for diagnostic purposes</td></tr>
 * </table>
 *
 * <p>All responses are JSON with the following structure:
 * <pre>
 * {
 *   "success": true | false,
 *   "result": {
 *     "message": "...",
 *     "data":    { ... } | [ ... ]
 *   }
 * }
 * </pre>
 *
 * @param <B> the bean type, must extend {@link com.sunilos.p4.bean.BaseBean}
 * @param <M> the model type, must extend {@link com.sunilos.p4.model.BaseModel}
 * @author Sunil Sahu
 * @version 1.0
 */
public abstract class BaseRestController<B extends BaseBean, M extends BaseModel> extends HttpServlet {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	protected abstract M getModel();

	/**
	 * Serialises an {@link ORSResponse} to JSON and writes it to the HTTP response
	 * with content-type {@code application/json}.
	 *
	 * @param res      the response object to serialise
	 * @param request  the incoming HTTP request (unused; kept for subclass access)
	 * @param response the HTTP response to write to
	 */
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
	 * For a URL like {@code DELETE /rest/{resource}/delete/1}:
	 * <ul>
	 * <li>{@code operation} attribute → {@code "delete"}</li>
	 * <li>{@code data} attribute → {@code "1"}</li>
	 * </ul>
	 *
	 * @param request  the incoming HTTP request
	 * @param response the HTTP response
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if an I/O error occurs
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
	 * If the path segment is {@code preload}, delegates to {@link #doGetPreload}.
	 * Otherwise, if the {@code id} query parameter is greater than zero the
	 * matching record is returned; if {@code id} is absent or zero, all records
	 * are returned.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>{@code GET /rest/{resource}?id=1} — returns a single record</li>
	 * <li>{@code GET /rest/{resource}}       — returns all records</li>
	 * <li>{@code GET /rest/{resource}/preload} — returns preload data</li>
	 * </ul>
	 *
	 * @param request  the HTTP request; reads optional {@code id} query parameter
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

	/**
	 * Handles {@code GET /rest/{resource}/preload} requests.
	 * <p>
	 * Override in subclasses to return dropdown or reference data needed before a
	 * form is rendered (e.g. a list of colleges for a student form). The default
	 * implementation returns a {@code 200} response with {@code success: false} and
	 * a "not implemented" message.
	 * <p>
	 * Example: {@code GET /rest/student/preload} → returns college list
	 *
	 * @param request  the HTTP request
	 * @param response the HTTP response; returns preload data as JSON
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if writing the response fails
	 */
	protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ORSResponse res = new ORSResponse(false, "Preload not implemented");
		sendResponse(res, request, response);
	}

	/**
	 * Handles {@code POST /rest/{resource}/search} requests.
	 * <p>
	 * Reads a JSON bean from the request body, uses it as search criteria, and
	 * returns the matching records.
	 * <p>
	 * Example: {@code POST /rest/{resource}/search} with a partial JSON bean as body
	 *
	 * @param request  the HTTP request carrying the search criteria as a JSON bean
	 * @param response the HTTP response; returns matching records as JSON
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if reading the request body or writing the response fails
	 */
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
	 * Handles HTTP POST requests. The operation is determined by the first URL
	 * path segment set by {@link #service}.
	 * <p>
	 * Supported operations:
	 * <ul>
	 * <li>{@code POST /rest/{resource}/add}    — inserts a new record; returns the
	 *     persisted bean</li>
	 * <li>{@code POST /rest/{resource}/search} — delegates to
	 *     {@link #doPostSearch}; returns matching records</li>
	 * </ul>
	 *
	 * @param request  the HTTP request carrying the JSON bean in its body
	 * @param response the HTTP response; returns the added record or search results as JSON
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if reading the request body or writing the response fails
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
	 * Returns an error if the id is missing or the record does not exist.
	 * Fetches and returns the refreshed bean after a successful update.
	 * <p>
	 * Example: {@code PUT /rest/{resource}/update} with a JSON bean body
	 *
	 * @param request  the HTTP request carrying the JSON bean in its body
	 * @param response the HTTP response; returns the updated bean as JSON
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if reading the request body or writing the response fails
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
	 * The record id is read from the {@code data} request attribute, which is
	 * populated by {@link #service} from the second URL path segment. Returns an
	 * error if the id is missing or the record does not exist. Returns the deleted
	 * bean on success.
	 * <p>
	 * Example: {@code DELETE /rest/{resource}/delete/1}
	 *
	 * @param request  the HTTP request; reads {@code data} attribute for the record id
	 * @param response the HTTP response; returns the deleted bean as JSON on success
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
	 * Useful for clients that need to check record existence or response headers
	 * without transferring the payload.
	 * <p>
	 * Example: {@code HEAD /rest/{resource}?id=1}
	 *
	 * @param request  the HTTP request
	 * @param response the HTTP response; headers are set but body is suppressed
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if an I/O error occurs
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
	 * <p>
	 * Example: {@code OPTIONS /rest/{resource}} →
	 * {@code Allow: GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE}
	 *
	 * @param request  the HTTP request
	 * @param response the HTTP response; sets the {@code Allow} header and returns {@code 200 OK}
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Allow", "GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Handles HTTP TRACE requests by echoing the incoming request line and all
	 * request headers back to the client as {@code message/http} content, which is
	 * the standard behaviour for diagnostic loop-back testing.
	 * <p>
	 * Example: {@code TRACE /rest/{resource}}
	 *
	 * @param request  the HTTP request whose headers are echoed
	 * @param response the HTTP response; content-type is {@code message/http}
	 * @throws ServletException if an unexpected servlet error occurs
	 * @throws IOException      if writing the response fails
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
	 * Populates the base {@link com.sunilos.p4.bean.BaseBean} fields of the given
	 * bean from the parsed JSON node.
	 * <p>
	 * Fields mapped: {@code id}, {@code createdBy}, {@code modifiedBy},
	 * {@code createdDatetime}, {@code modifiedDatetime}.
	 * <p>
	 * Subclasses must call {@code super.jsonToBean(jsonNode, bean)} and then map
	 * their own entity-specific fields.
	 *
	 * @param jsonNode the parsed JSON object from the request body
	 * @param bean     the target bean to populate
	 * @return the populated bean (same instance passed in)
	 */
	public B jsonToBean(JsonNode jsonNode, B bean) {
		bean.setId(DataUtility.getLong(getJsonValue(jsonNode, "id")));
		bean.setCreatedDatetime(new Timestamp(DataUtility.getLong(getJsonValue(jsonNode, "createdDatetime"))));
		bean.setModifiedDatetime(new Timestamp(DataUtility.getLong(getJsonValue(jsonNode, "modifiedDatetime"))));
		bean.setModifiedBy(getJsonValue(jsonNode, "modifiedBy"));
		bean.setCreatedBy(getJsonValue(jsonNode, "createdBy"));
		return bean;
	}

	/**
	 * Returns a fresh, empty bean instance for the entity managed by this
	 * controller. Called before each {@link #jsonToBean} invocation.
	 *
	 * @return a new, unpopulated bean of type {@code B}
	 */
	public abstract B getBean();

	/**
	 * Safely reads a string value from a {@link JsonNode} by field name.
	 *
	 * @param jsonNode the parsed JSON object
	 * @param key      the field name to look up
	 * @return the field value as a {@code String}, or {@code null} if the field is
	 *         absent or explicitly {@code null} in the JSON
	 */
	public String getJsonValue(JsonNode jsonNode, String key) {
		JsonNode val = null;
		val = jsonNode.get(key);
		if (val != null) {
			return val.asText();
		} else {
			return null;
		}
	}

	/**
	 * Safely reads a {@code long} value from a {@link JsonNode} by field name.
	 *
	 * @param jsonNode the parsed JSON object
	 * @param key      the field name to look up
	 * @return the field value as a {@code long}, or {@code 0} if the field is
	 *         absent or explicitly {@code null} in the JSON
	 */
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
