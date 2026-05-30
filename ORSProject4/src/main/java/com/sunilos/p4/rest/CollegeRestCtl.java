package com.sunilos.p4.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunilos.p4.bean.CollegeBean;
import com.sunilos.p4.model.CollegeModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * REST controller for College CRUD operations.
 * <p>
 * Mapped to {@code /rest/collegectl/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/collegectl?id=1} — get by id</li>
 * <li>{@code GET    /rest/collegectl} — get all</li>
 * <li>{@code POST   /rest/collegectl/add} — add new college</li>
 * <li>{@code PUT    /rest/collegectl/update} — update college</li>
 * <li>{@code DELETE /rest/collegectl/delete/1} — delete by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/college/*" })
public class CollegeRestCtl extends BaseRestController<CollegeBean, CollegeModel> {

    private static final CollegeModel MODEL = new CollegeModel();

    @Override
    public CollegeBean jsonToBean(JsonNode jsonNode, CollegeBean bean) {
        super.jsonToBean(jsonNode, bean);
        bean.setName(getJsonValue(jsonNode, "name"));
        bean.setAddress(getJsonValue(jsonNode, "address"));
        bean.setState(getJsonValue(jsonNode, "state"));
        bean.setCity(getJsonValue(jsonNode, "city"));
        bean.setPhoneNo(getJsonValue(jsonNode, "phoneNo"));
        return bean;
    }

    @Override
    protected void doGetPreload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected CollegeModel getModel() {
        return MODEL;
    }

    @Override
    public CollegeBean getBean() {
        return new CollegeBean();
    }

}
