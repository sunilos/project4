package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.model.RoleModel;

import jakarta.servlet.annotation.WebServlet;

/**
 * Report servlet that generates a Role list report in PDF or DOC format.
 * Mapped to {@code /ctl/RoleReportCtl}; add {@code ?type=doc} for Word output.
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseReportCtl
 */
@WebServlet("/ctl/RoleReportCtl")
public class RoleReportCtl extends BaseReportCtl<RoleBean> {

    /**
     * Fetches all roles from the database.
     *
     * @return list of all {@link RoleBean} records
     */
    public List<RoleBean> getList() {
        RoleModel model = new RoleModel();
        @SuppressWarnings("unchecked")
        List<RoleBean> roles = model.list();
        return roles;
    }

    /**
     * Returns the JRXML template path for the role list report.
     *
     * @return {@link ORSView#ROLE_REPORT_VIEW}
     */
    public String getView() {
        return ORSView.ROLE_REPORT_VIEW;
    }

    /**
     * Returns the ServletContext cache key for the compiled role report.
     *
     * @return {@code "ROLE_LIST_COMPILED_REPORT"}
     */
    public String getCompiledReportKey() {
        return "ROLE_LIST_COMPILED_REPORT";
    }

}
