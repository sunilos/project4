<%@page import="com.sunilos.p4.ctl.GetMarksheetCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.MarksheetBean" scope="request"></jsp:useBean>

<%
    String _suc = ServletUtility.getSuccessMessage(request);
    String _err = ServletUtility.getErrorMessage(request);
    boolean hasResult = bean.getRollNo() != null && bean.getRollNo().trim().length() > 0;
%>

<div class="container py-4" style="max-width:600px;">

  <!-- Search Card -->
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden mb-4">
    <div class="card-header text-white border-0 py-3 px-4"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <h5 class="mb-0 fw-bold"><i class="bi bi-search me-2"></i> Get Marksheet</h5>
    </div>
    <div class="card-body px-4 py-4">

      <% if(_suc != null && !_suc.isEmpty()) { %>
      <div class="alert alert-success py-2"><i class="bi bi-check-circle-fill me-2"></i><%=_suc%></div>
      <% } %>
      <% if(_err != null && !_err.isEmpty()) { %>
      <div class="alert alert-danger py-2"><i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%></div>
      <% } %>

      <form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="POST">
        <label class="form-label fw-semibold">Roll No <span class="text-danger">*</span></label>
        <div class="input-group">
          <input type="text" name="rollNo" class="form-control form-control-lg"
                 placeholder="Enter Roll Number"
                 value="<%=ServletUtility.getParameter("rollNo", request)%>">
          <button type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>"
                  class="btn btn-primary px-4">
            <i class="bi bi-search me-1"></i> Search
          </button>
        </div>
        <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("rollNo", request)%></div>
      </form>
    </div>
  </div>

  <!-- Result Card -->
  <% if(hasResult) { %>
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
    <div class="card-header border-0 py-3 px-4 bg-success text-white">
      <h6 class="mb-0 fw-bold"><i class="bi bi-file-earmark-check-fill me-2"></i> Marksheet Result</h6>
    </div>
    <div class="card-body p-0">
      <table class="table table-borderless mb-0">
        <tbody>
          <tr class="border-bottom">
            <th class="ps-4 py-3 text-muted" style="width:40%">Roll No</th>
            <td class="fw-semibold py-3"><%=DataUtility.getStringData(bean.getRollNo())%></td>
          </tr>
          <tr class="border-bottom">
            <th class="ps-4 py-3 text-muted">Student Name</th>
            <td class="fw-semibold py-3"><%=DataUtility.getStringData(bean.getName())%></td>
          </tr>
          <tr class="border-bottom">
            <th class="ps-4 py-3 text-muted">Physics</th>
            <td class="py-3">
              <span class="badge bg-primary rounded-pill fs-6 px-3"><%=DataUtility.getStringData(bean.getPhysics())%></span>
            </td>
          </tr>
          <tr class="border-bottom">
            <th class="ps-4 py-3 text-muted">Chemistry</th>
            <td class="py-3">
              <span class="badge bg-success rounded-pill fs-6 px-3"><%=DataUtility.getStringData(bean.getChemistry())%></span>
            </td>
          </tr>
          <tr>
            <th class="ps-4 py-3 text-muted">Maths</th>
            <td class="py-3">
              <span class="badge bg-warning text-dark rounded-pill fs-6 px-3"><%=DataUtility.getStringData(bean.getMaths())%></span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <% } %>

</div>
