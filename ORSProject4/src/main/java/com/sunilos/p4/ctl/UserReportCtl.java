package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.model.UserModel;

import jakarta.servlet.annotation.WebServlet;

/**
 * Report servlet that generates a User list report in PDF or DOC format.
 * Mapped to {@code /ctl/UserReportCtl}; add {@code ?type=doc} for Word output.
 *
 * @author Rays EdTech
 * @version 1.0
 * @see BaseReportCtl
 */
@WebServlet("/ctl/UserReportCtl")
public class UserReportCtl extends BaseReportCtl<UserBean> {

    /**
     * Fetches all users from the database.
     *
     * @return list of all {@link UserBean} records
     */
    public List<UserBean> getList() {
        UserModel model = new UserModel();
        @SuppressWarnings("unchecked")
        List<UserBean> users = model.list();
        return users;
    }

    /**
     * Returns the JRXML template path for the user list report.
     *
     * @return {@link ORSView#USER_REPORT_VIEW}
     */
    public String getView() {
        return ORSView.USER_REPORT_VIEW;
    }

    /**
     * Returns the ServletContext cache key for the compiled user report.
     *
     * @return {@code "USER_LIST_COMPILED_REPORT"}
     */
    public String getCompiledReportKey() {
        return "USER_LIST_COMPILED_REPORT";
    }

}
