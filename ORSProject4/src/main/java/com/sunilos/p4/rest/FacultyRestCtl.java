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
 * Mapped to {@code /rest/facultyctl/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/facultyctl?id=1} — get by id</li>
 * <li>{@code GET    /rest/facultyctl} — get all</li>
 * <li>{@code GET    /rest/facultyctl/preload} — get preload data (college list)</li>
 * <li>{@code POST   /rest/facultyctl/add} — add new faculty</li>
 * <li>{@code PUT    /rest/facultyctl/update} — update faculty</li>
 * <li>{@code DELETE /rest/facultyctl/delete/1} — delete by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/faculty/*" })
public class FacultyRestCtl extends BaseRestController<FacultyBean, FacultyModel> {

    private static final FacultyModel MODEL = new FacultyModel();

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

    @Override
    protected FacultyModel getModel() {
        return MODEL;
    }

    @Override
    public FacultyBean getBean() {
        return new FacultyBean();
    }

}
