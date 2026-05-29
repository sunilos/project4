<%@ taglib uri="http://www.sunilos.com/ors-tags" prefix="ors" %>
<%@page import="com.sunilos.p4.ctl.MarksheetListCtl"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<%
    int pageNo   = ServletUtility.getPageNo(request);
    int pageSize = ServletUtility.getPageSize(request);
    int index    = ((pageNo - 1) * pageSize) + 1;
    List list    = ServletUtility.getList(request);
    Iterator<MarksheetBean> it = list.iterator();
    String _err  = ServletUtility.getErrorMessage(request);
%>

<div class="container-fluid px-4 py-4" style="max-width:1050px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-3 px-4 d-flex justify-content-between align-items-center"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <h5 class="mb-0 fw-bold"><i class="bi bi-file-earmark-text-fill me-2"></i> <ors:message key="marksheet.list.title"/></h5>
      <div class="d-flex gap-2">
        <a href="<%=ORSView.MARKSHEET_REPORT_CTL%>" target="_blank" class="btn btn-sm btn-warning fw-semibold">
          <i class="bi bi-file-earmark-pdf me-1"></i> Print PDF
        </a>
        <a href="<%=ORSView.MARKSHEET_REPORT_CTL%>?type=doc" target="_blank" class="btn btn-sm btn-info fw-semibold">
          <i class="bi bi-file-earmark-word me-1"></i> Print DOC
        </a>
        <a href="MarksheetCtl" class="btn btn-sm btn-light text-primary fw-semibold">
          <i class="bi bi-plus-circle me-1"></i> <ors:message key="marksheet.add"/>
        </a>
      </div>
    </div>

    <form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="POST">
      <input type="hidden" name="pageNo"   value="<%=pageNo%>">
      <input type="hidden" name="pageSize" value="<%=pageSize%>">

      <div class="p-3 bg-light border-bottom d-flex flex-wrap gap-2 align-items-center">
        <input type="text" name="name" class="form-control form-control-sm" style="max-width:180px;"
               placeholder="Student Name" value="<%=ServletUtility.getParameter("name", request)%>">
        <input type="text" name="rollNo" class="form-control form-control-sm" style="max-width:160px;"
               placeholder="Roll No" value="<%=ServletUtility.getParameter("rollNo", request)%>">
        <button type="submit" name="operation" value="<%=BaseCtl.OP_SEARCH%>" class="btn btn-primary btn-sm">
          <i class="bi bi-search me-1"></i> <ors:message key="button.search"/>
        </button>
        <button type="submit" name="operation" value="<%=BaseCtl.OP_DELETE%>" class="btn btn-danger btn-sm ms-auto"
                onclick="return confirm('Delete selected marksheets?')">
          <i class="bi bi-trash me-1"></i> <ors:message key="button.delete.selected"/>
        </button>
      </div>

      <% if(_err != null && !_err.isEmpty()) { %>
      <div class="alert alert-danger py-2 mx-3 mt-3"><i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%></div>
      <% } %>

      <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th width="40"><input type="checkbox" onclick="document.querySelectorAll('input[name=ids]').forEach(c=>c.checked=this.checked)"></th>
              <th>#</th>
              <th>ID</th>
              <th><ors:message key="marksheet.rollno"/></th>
              <th><ors:message key="marksheet.student.name"/></th>
              <th class="text-center"><ors:message key="marksheet.physics"/></th>
              <th class="text-center"><ors:message key="marksheet.chemistry"/></th>
              <th class="text-center"><ors:message key="marksheet.maths"/></th>
              <th><ors:message key="label.action"/></th>
            </tr>
          </thead>
          <tbody>
            <% while(it.hasNext()) { MarksheetBean bean = it.next(); %>
            <tr>
              <td><input type="checkbox" name="ids" value="<%=bean.getId()%>"></td>
              <td class="text-muted small"><%=index++%></td>
              <td class="text-muted small"><%=bean.getId()%></td>
              <td class="fw-semibold"><%=bean.getRollNo()%></td>
              <td><%=bean.getName()%></td>
              <td class="text-center"><%=bean.getPhysics()%></td>
              <td class="text-center"><%=bean.getChemistry()%></td>
              <td class="text-center"><%=bean.getMaths()%></td>
              <td>
                <a href="MarksheetCtl?id=<%=bean.getId()%>" class="btn btn-sm btn-outline-primary">
                  <i class="bi bi-pencil"></i> <ors:message key="button.edit"/>
                </a>
              </td>
            </tr>
            <% } %>
          </tbody>
        </table>
      </div>

      <div class="p-3 border-top">
        <%@ include file="ListFooter.jsp"%>
      </div>
    </form>
  </div>
</div>
