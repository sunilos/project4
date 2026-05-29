package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.model.RoleModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/RoleReportCtl")
public class RoleReportCtl extends BaseReportCtl<RoleBean> {

    public List<RoleBean> getList() {
        RoleModel model = new RoleModel();
        @SuppressWarnings("unchecked")
        List<RoleBean> roles = model.list();
        return roles;
    }

    public String getView() {
        return ORSView.ROLE_REPORT_VIEW;
    }

    public String getCompiledReportKey() {
        return "ROLE_LIST_COMPILED_REPORT";
    }

}
