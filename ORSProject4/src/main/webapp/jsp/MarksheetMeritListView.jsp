<%@ taglib uri="http://www.sunilos.com/ors-tags" prefix="ors" %>
<%@page import="com.sunilos.p4.ctl.MarksheetMeritListCtl"%>
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

<div class="container-fluid px-4 py-4" style="max-width:950px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-3 px-4"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <h5 class="mb-0 fw-bold"><i class="bi bi-trophy-fill me-2"></i> <ors:message key="marksheet.meritlist.title"/></h5>
    </div>

    <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">

      <% if(_err != null && !_err.isEmpty()) { %>
      <div class="alert alert-danger py-2 mx-3 mt-3"><i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%></div>
      <% } %>

      <div class="table-responsive">
        <table class="table table-hover align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th width="60" class="text-center"><ors:message key="marksheet.rank"/></th>
              <th><ors:message key="marksheet.rollno"/></th>
              <th><ors:message key="marksheet.student.name"/></th>
              <th class="text-center"><ors:message key="marksheet.physics"/></th>
              <th class="text-center"><ors:message key="marksheet.chemistry"/></th>
              <th class="text-center"><ors:message key="marksheet.maths"/></th>
            </tr>
          </thead>
          <tbody>
            <% int rank = index; while(it.hasNext()) { MarksheetBean bean = it.next(); %>
            <tr>
              <td class="text-center">
                <% if(rank == 1) { %>
                  <span class="badge bg-warning text-dark fs-6"><i class="bi bi-trophy-fill"></i> <%=rank++%></span>
                <% } else if(rank == 2) { %>
                  <span class="badge bg-secondary fs-6"><%=rank++%></span>
                <% } else if(rank == 3) { %>
                  <span class="badge" style="background:#cd7f32;font-size:1rem;"><%=rank++%></span>
                <% } else { %>
                  <span class="text-muted"><%=rank++%></span>
                <% } %>
              </td>
              <td class="fw-semibold"><%=bean.getRollNo()%></td>
              <td><%=bean.getName()%></td>
              <td class="text-center"><span class="badge bg-primary rounded-pill px-3"><%=bean.getPhysics()%></span></td>
              <td class="text-center"><span class="badge bg-success rounded-pill px-3"><%=bean.getChemistry()%></span></td>
              <td class="text-center"><span class="badge bg-warning text-dark rounded-pill px-3"><%=bean.getMaths()%></span></td>
            </tr>
            <% } %>
          </tbody>
        </table>
      </div>

      <div class="p-3 border-top d-flex justify-content-end">
        <button type="submit" name="operation" value="<%=MarksheetMeritListCtl.OP_BACK%>"
                class="btn btn-outline-secondary">
          <i class="bi bi-arrow-left me-1"></i> <ors:message key="button.back"/>
        </button>
      </div>
    </form>
  </div>
</div>
