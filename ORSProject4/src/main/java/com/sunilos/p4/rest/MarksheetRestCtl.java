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
 * Mapped to {@code /rest/marksheetctl/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/marksheetctl?id=1} — get by id</li>
 * <li>{@code GET    /rest/marksheetctl} — get all</li>
 * <li>{@code GET    /rest/marksheetctl/preload} — get preload data (student list)</li>
 * <li>{@code POST   /rest/marksheetctl/add} — add new marksheet</li>
 * <li>{@code PUT    /rest/marksheetctl/update} — update marksheet</li>
 * <li>{@code DELETE /rest/marksheetctl/delete/1} — delete by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/marksheet/*" })
public class MarksheetRestCtl extends BaseRestController<MarksheetBean, MarksheetModel> {

    private static final MarksheetModel MODEL = new MarksheetModel();

    @Override
    public MarksheetBean jsonToBean(JsonNode jsonNode, MarksheetBean bean) {
        super.jsonToBean(jsonNode, bean);
        bean.setRollNo(getJsonValue(jsonNode, "rollNo"));
        bean.setName(getJsonValue(jsonNode, "name"));
        bean.setStudentId(getJsonLongValue(jsonNode, "studentId"));
        bean.setPhysics(getJsonNode(jsonNode, "physics"));
        bean.setChemistry(getJsonNode(jsonNode, "chemistry"));
        bean.setMaths(getJsonNode(jsonNode, "maths"));
        return bean;
    }

    private Integer getJsonNode(JsonNode jsonNode, String key) {
        JsonNode val = jsonNode.get(key);
        return (val != null && !val.isNull()) ? val.asInt() : null;
    }

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

    @Override
    protected MarksheetModel getModel() {
        return MODEL;
    }

    @Override
    public MarksheetBean getBean() {
        return new MarksheetBean();
    }

}
