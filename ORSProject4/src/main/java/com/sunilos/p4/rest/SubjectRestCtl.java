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
 * Mapped to {@code /rest/subjectctl/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/subjectctl?id=1} — get by id</li>
 * <li>{@code GET    /rest/subjectctl} — get all</li>
 * <li>{@code POST   /rest/subjectctl/add} — add new subject</li>
 * <li>{@code PUT    /rest/subjectctl/update} — update subject</li>
 * <li>{@code DELETE /rest/subjectctl/delete/1} — delete by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/subject/*" })
public class SubjectRestCtl extends BaseRestController<SubjectBean, SubjectModel> {

    private static final SubjectModel MODEL = new SubjectModel();

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
     * Returns the shared stateless {@link SubjectModel} instance.
     *
     * @return singleton {@link SubjectModel}
     */
    @Override
    protected SubjectModel getModel() {
        return MODEL;
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
