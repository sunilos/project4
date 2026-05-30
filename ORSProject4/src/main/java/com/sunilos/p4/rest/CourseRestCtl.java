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
 * Mapped to {@code /rest/coursectl/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/coursectl?id=1} — get by id</li>
 * <li>{@code GET    /rest/coursectl} — get all</li>
 * <li>{@code POST   /rest/coursectl/add} — add new course</li>
 * <li>{@code PUT    /rest/coursectl/update} — update course</li>
 * <li>{@code DELETE /rest/coursectl/delete/1} — delete by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/course/*" })
public class CourseRestCtl extends BaseRestController<CourseBean, CourseModel> {

    private static final CourseModel MODEL = new CourseModel();

    @Override
    public CourseBean jsonToBean(JsonNode jsonNode, CourseBean bean) {
        super.jsonToBean(jsonNode, bean);
        bean.setName(getJsonValue(jsonNode, "name"));
        bean.setDescription(getJsonValue(jsonNode, "description"));
        bean.setDuration(getJsonValue(jsonNode, "duration"));
        return bean;
    }

    @Override
    protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected CourseModel getModel() {
        return MODEL;
    }

    @Override
    public CourseBean getBean() {
        return new CourseBean();
    }

}
