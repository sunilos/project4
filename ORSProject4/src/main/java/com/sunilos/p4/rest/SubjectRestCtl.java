package com.sunilos.p4.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.ORSResponse;
import com.sunilos.p4.bean.SubjectBean;
import com.sunilos.p4.model.CourseModel;
import com.sunilos.p4.model.SubjectModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for Subject CRUD operations.
 * <p>
 * Mapped to {@code /rest/subject/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/subject?id=1}      — get a subject by id</li>
 * <li>{@code GET    /rest/subject}            — get all subjects</li>
 * <li>{@code GET    /rest/subject/preload}    — get preload data (course list)</li>
 * <li>{@code POST   /rest/subject/add}        — add a new subject</li>
 * <li>{@code POST   /rest/subject/search}     — search subjects by criteria</li>
 * <li>{@code PUT    /rest/subject/update}     — update an existing subject</li>
 * <li>{@code DELETE /rest/subject/delete/1}   — delete a subject by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/subject/*" })
public class SubjectRestCtl extends BaseRestController<SubjectBean, SubjectModel> {

    /**
     * Maps JSON fields to a {@link SubjectBean}, delegating base fields to
     * {@link BaseRestController#jsonToBean} before adding subject-specific fields.
     *
     * @param jsonNode the parsed JSON request body
     * @param bean     the target bean to populate
     * @return the populated {@link SubjectBean}
     */
    @Override
    public SubjectBean jsonToBean(JsonNode jsonNode, SubjectBean bean) {
        super.jsonToBean(jsonNode, bean);
        bean.setName(getJsonValue(jsonNode, "name"));
        bean.setDescription(getJsonValue(jsonNode, "description"));
        bean.setCourseId(getJsonLongValue(jsonNode, "courseId"));
        return bean;
    }

    /**
     * Handles {@code GET /rest/subject/preload}.
     * Returns the full list of courses, used to populate the course dropdown
     * on the subject form.
     *
     * @param request  the HTTP request
     * @param response the HTTP response; returns {@code { "preload": { "courseList": [...] } }}
     * @throws ServletException if an unexpected servlet error occurs
     * @throws IOException      if writing the response fails
     */
    @Override
    protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CourseModel model = new CourseModel();
        List l = model.list();
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", l);

        ORSResponse res = new ORSResponse(true);
        res.addResult("preload", map);
        sendResponse(res, request, response);
    }

    /**
     * Returns a new {@link SubjectModel} instance; override in tests to inject a mock.
     *
     * @return new {@link SubjectModel}
     */
    @Override
    protected SubjectModel getModel() {
        return new SubjectModel();
    }

    /**
     * Returns a new {@link SubjectBean} instance for each request.
     *
     * @return new {@link SubjectBean}
     */
    @Override
    public SubjectBean getBean() {
        return new SubjectBean();
    }

}
