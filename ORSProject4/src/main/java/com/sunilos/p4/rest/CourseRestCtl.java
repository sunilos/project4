package com.sunilos.p4.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.CourseBean;
import com.sunilos.p4.model.CourseModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * REST controller for Course CRUD operations.
 * <p>
 * Mapped to {@code /rest/course/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/course?id=1}      — get a course by id</li>
 * <li>{@code GET    /rest/course}            — get all courses</li>
 * <li>{@code POST   /rest/course/add}        — add a new course</li>
 * <li>{@code POST   /rest/course/search}     — search courses by criteria</li>
 * <li>{@code PUT    /rest/course/update}     — update an existing course</li>
 * <li>{@code DELETE /rest/course/delete/1}   — delete a course by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/course/*" })
public class CourseRestCtl extends BaseRestController<CourseBean, CourseModel> {

    /**
     * Maps JSON fields to a {@link CourseBean}, delegating base fields to
     * {@link BaseRestController#jsonToBean} before adding course-specific fields.
     *
     * @param jsonNode the parsed JSON request body
     * @param bean     the target bean to populate
     * @return the populated {@link CourseBean}
     */
    @Override
    public CourseBean jsonToBean(JsonNode jsonNode, CourseBean bean) {
        super.jsonToBean(jsonNode, bean);
        bean.setName(getJsonValue(jsonNode, "name"));
        bean.setDescription(getJsonValue(jsonNode, "description"));
        bean.setDuration(getJsonValue(jsonNode, "duration"));
        return bean;
    }

    /**
     * No preload data is required for courses; this implementation is intentionally empty.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if an unexpected servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a new {@link CourseModel} instance; override in tests to inject a mock.
     *
     * @return new {@link CourseModel}
     */
    @Override
    protected CourseModel getModel() {
        return new CourseModel();
    }

    /**
     * Returns a new {@link CourseBean} instance for each request.
     *
     * @return new {@link CourseBean}
     */
    @Override
    public CourseBean getBean() {
        return new CourseBean();
    }

}
