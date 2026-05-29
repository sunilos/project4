package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.MarksheetBean;
import com.sunilos.p4.model.MarksheetModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/MarksheetReportCtl")
public class MarksheetReportCtl extends BaseReportCtl<MarksheetBean> {

    public List<MarksheetBean> getList() {
        MarksheetModel model = new MarksheetModel();
        @SuppressWarnings("unchecked")
        List<MarksheetBean> marksheets = model.list();
        return marksheets;
    }

    public String getView() {
        return ORSView.MARKSHEET_REPORT_VIEW;
    }

    public String getCompiledReportKey() {
        return "MARKSHEET_LIST_COMPILED_REPORT";
    }

}
