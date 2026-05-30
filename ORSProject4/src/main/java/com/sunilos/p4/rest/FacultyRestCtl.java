package com.sunilos.p4.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.FacultyBean;
import com.sunilos.p4.bean.ORSResponse;
import com.sunilos.p4.model.CollegeModel;
import com.sunilos.p4.model.FacultyModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for Faculty CRUD operations.
 * <p>
 * Mapped to {@code /rest/faculty/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/faculty?id=1}      — get a faculty member by id</li>
 * <li>{@code GET    /rest/faculty}            — get all faculty members</li>
 * <li>{@code GET    /rest/faculty/preload}    — get preload data (college list)</li>
 * <li>{@code POST   /rest/faculty/add}        — add a new faculty member</li>
 * <li>{@code POST   /rest/faculty/search}     — search faculty by criteria</li>
 * <li>{@code PUT    /rest/faculty/update}     — update an existing faculty member</li>
 * <li>{@code DELETE /rest/faculty/delete/1}   — delete a faculty member by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/faculty/*" })
public class FacultyRestCtl extends BaseRestController<FacultyBean, FacultyModel> {

    /**
     * Maps JSON fields to a {@link FacultyBean}, delegating base fields to
     * {@link BaseRestController#jsonToBean} before adding faculty-specific fields.
     * <p>
     * Note: {@code dob} is omitted — date parsing from JSON is not yet implemented.
     *
     * @param jsonNode the parsed JSON request body
     * @param bean     the target bean to populate
     * @return the populated {@link FacultyBean}
     */
    @Override
    public FacultyBean jsonToBean(JsonNode jsonNode, FacultyBean bean) {
        super.jsonToBean(jsonNode, bean);
        bean.setCollegeId(getJsonLongValue(jsonNode, "collegeId"));
        bean.setFirstName(getJsonValue(jsonNode, "firstName"));
        bean.setLastName(getJsonValue(jsonNode, "lastName"));
        bean.setEmail(getJsonValue(jsonNode, "email"));
        bean.setMobileNo(getJsonValue(jsonNode, "mobileNo"));
        bean.setAddress(getJsonValue(jsonNode, "address"));
        bean.setGender(getJsonValue(jsonNode, "gender"));
        // dob omitted — date parsing from JSON not yet implemented
        return bean;
    }

    /**
     * Handles {@code GET /rest/faculty/preload}.
     * Returns the full list of colleges, used to populate the college dropdown
     * on the faculty form.
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
     * Returns a new {@link FacultyModel} instance; override in tests to inject a mock.
     *
     * @return new {@link FacultyModel}
     */
    @Override
    protected FacultyModel getModel() {
        return new FacultyModel();
    }

    /**
     * Returns a new {@link FacultyBean} instance for each request.
     *
     * @return new {@link FacultyBean}
     */
    @Override
    public FacultyBean getBean() {
        return new FacultyBean();
    }

}
