package com.sunilos.p4.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.MarksheetBean;
import com.sunilos.p4.bean.ORSResponse;
import com.sunilos.p4.model.MarksheetModel;
import com.sunilos.p4.model.StudentModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for Marksheet CRUD operations.
 * <p>
 * Mapped to {@code /rest/marksheet/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/marksheet?id=1}      — get a marksheet by id</li>
 * <li>{@code GET    /rest/marksheet}            — get all marksheets</li>
 * <li>{@code GET    /rest/marksheet/preload}    — get preload data (student list)</li>
 * <li>{@code POST   /rest/marksheet/add}        — add a new marksheet</li>
 * <li>{@code POST   /rest/marksheet/search}     — search marksheets by criteria</li>
 * <li>{@code PUT    /rest/marksheet/update}     — update an existing marksheet</li>
 * <li>{@code DELETE /rest/marksheet/delete/1}   — delete a marksheet by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/marksheet/*" })
public class MarksheetRestCtl extends BaseRestController<MarksheetBean, MarksheetModel> {

    /**
     * Maps JSON fields to a {@link MarksheetBean}, delegating base fields to
     * {@link BaseRestController#jsonToBean} before adding marksheet-specific fields.
     *
     * @param jsonNode the parsed JSON request body
     * @param bean     the target bean to populate
     * @return the populated {@link MarksheetBean}
     */
    @Override
    public MarksheetBean jsonToBean(JsonNode jsonNode, MarksheetBean bean) {
        super.jsonToBean(jsonNode, bean);
        bean.setRollNo(getJsonValue(jsonNode, "rollNo"));
        bean.setName(getJsonValue(jsonNode, "name"));
        bean.setStudentId(getJsonLongValue(jsonNode, "studentId"));
        bean.setPhysics(getJsonInteger(jsonNode, "physics"));
        bean.setChemistry(getJsonInteger(jsonNode, "chemistry"));
        bean.setMaths(getJsonInteger(jsonNode, "maths"));
        return bean;
    }

    /**
     * Safely reads an {@code Integer} value from a {@link JsonNode} by field name.
     * Returns {@code null} if the field is absent or explicitly {@code null} in the JSON,
     * which preserves the distinction between "not provided" and zero marks.
     *
     * @param jsonNode the parsed JSON object
     * @param key      the field name to look up
     * @return the field value as an {@link Integer}, or {@code null} if absent or null
     */
    private Integer getJsonInteger(JsonNode jsonNode, String key) {
        JsonNode val = jsonNode.get(key);
        return (val != null && !val.isNull()) ? val.asInt() : null;
    }

    /**
     * Handles {@code GET /rest/marksheet/preload}.
     * Returns the full list of students, used to populate the student dropdown
     * on the marksheet form.
     *
     * @param request  the HTTP request
     * @param response the HTTP response; returns {@code { "preload": { "studentList": [...] } }}
     * @throws ServletException if an unexpected servlet error occurs
     * @throws IOException      if writing the response fails
     */
    @Override
    protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StudentModel model = new StudentModel();
        List<?> l = model.list();
        HashMap<String, Object> map = new HashMap<>();
        map.put("studentList", l);

        ORSResponse res = new ORSResponse(true);
        res.addResult("preload", map);
        sendResponse(res, request, response);
    }

    /**
     * Returns a new {@link MarksheetModel} instance; override in tests to inject a mock.
     *
     * @return new {@link MarksheetModel}
     */
    @Override
    protected MarksheetModel getModel() {
        return new MarksheetModel();
    }

    /**
     * Returns a new {@link MarksheetBean} instance for each request.
     *
     * @return new {@link MarksheetBean}
     */
    @Override
    public MarksheetBean getBean() {
        return new MarksheetBean();
    }

}
