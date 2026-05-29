package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.CollegeBean;
import com.sunilos.p4.model.CollegeModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/CollegeReportCtl")
public class CollegeReportCtl extends BaseReportCtl<CollegeBean> {

    public List<CollegeBean> getList() {
        CollegeModel model = new CollegeModel();
        @SuppressWarnings("unchecked")
        List<CollegeBean> colleges = model.list();
        return colleges;
    }

    public String getView() {
        return ORSView.COLLEGE_REPORT_VIEW;
    }

    public String getCompiledReportKey() {
        return "COLLEGE_LIST_COMPILED_REPORT";
    }

}
