package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.model.UserModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/UserReportCtl")
public class UserReportCtl extends BaseReportCtl<UserBean> {

    public List<UserBean> getList() {
        UserModel model = new UserModel();
        @SuppressWarnings("unchecked")
        List<UserBean> users = model.list();
        return users;
    }

    public String getView() {
        return ORSView.USER_REPORT_VIEW;
    }

    public String getCompiledReportKey() {
        return "USER_LIST_COMPILED_REPORT";
    }

}
