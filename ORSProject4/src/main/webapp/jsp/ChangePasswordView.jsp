<%@page import="com.sunilos.p4.ctl.ChangePasswordCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<%
    String _suc = ServletUtility.getSuccessMessage(request);
    String _err = ServletUtility.getErrorMessage(request);
%>

<div class="container py-4" style="max-width:560px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-3 px-4"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <h5 class="mb-0 fw-bold">
        <i class="bi bi-key-fill me-2"></i> Change Password
      </h5>
    </div>

    <div class="card-body px-4 py-4">

      <% if(_suc != null && !_suc.isEmpty()) { %>
      <div class="alert alert-success py-2"><i class="bi bi-check-circle-fill me-2"></i><%=_suc%></div>
      <% } %>
      <% if(_err != null && !_err.isEmpty()) { %>
      <div class="alert alert-danger py-2"><i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%></div>
      <% } %>

      <form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="POST">

        <div class="mb-3">
          <label class="form-label fw-semibold">Old Password <span class="text-danger">*</span></label>
          <input type="password" name="oldPassword" class="form-control"
                 value="<%=DataUtility.getString(request.getParameter("oldPassword") == null ? "" : DataUtility.getString(request.getParameter("oldPassword")))%>">
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("oldPassword", request)%></div>
        </div>

        <div class="mb-3">
          <label class="form-label fw-semibold">New Password <span class="text-danger">*</span></label>
          <input type="password" name="newPassword" class="form-control"
                 value="<%=DataUtility.getString(request.getParameter("newPassword") == null ? "" : DataUtility.getString(request.getParameter("newPassword")))%>">
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("newPassword", request)%></div>
        </div>

        <div class="mb-4">
          <label class="form-label fw-semibold">Confirm Password <span class="text-danger">*</span></label>
          <input type="password" name="confirmPassword" class="form-control"
                 value="<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? "" : DataUtility.getString(request.getParameter("confirmPassword")))%>">
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></div>
        </div>

        <div class="pt-2 border-top">
          <button type="submit" name="operation" value="<%=ChangePasswordCtl.OP_SAVE%>" class="btn btn-primary">
            <i class="bi bi-shield-lock me-1"></i> Update Password
          </button>
        </div>
      </form>
    </div>
  </div>
</div>
