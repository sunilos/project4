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
 * Mapped to {@code /rest/college/*}. Supports:
 * <ul>
 * <li>{@code GET    /rest/college?id=1}      — get a college by id</li>
 * <li>{@code GET    /rest/college}            — get all colleges</li>
 * <li>{@code POST   /rest/college/add}        — add a new college</li>
 * <li>{@code POST   /rest/college/search}     — search colleges by criteria</li>
 * <li>{@code PUT    /rest/college/update}     — update an existing college</li>
 * <li>{@code DELETE /rest/college/delete/1}   — delete a college by id</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 * @see BaseRestController
 */
@WebServlet(urlPatterns = { "/rest/college/*" })
public class CollegeRestCtl extends BaseRestController<CollegeBean, CollegeModel> {

    /**
     * Maps JSON fields to a {@link CollegeBean}, delegating base fields to
     * {@link BaseRestController#jsonToBean} before adding college-specific fields.
     *
     * @param jsonNode the parsed JSON request body
     * @param bean     the target bean to populate
     * @return the populated {@link CollegeBean}
     */
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

    /**
     * No preload data is required for colleges; this implementation is intentionally empty.
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
     * Returns a new {@link CollegeModel} instance; override in tests to inject a mock.
     *
     * @return new {@link CollegeModel}
     */
    @Override
    protected CollegeModel getModel() {
        return new CollegeModel();
    }

    /**
     * Returns a new {@link CollegeBean} instance for each request.
     *
     * @return new {@link CollegeBean}
     */
    @Override
    public CollegeBean getBean() {
        return new CollegeBean();
    }

}
