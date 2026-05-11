<%@page import="com.sunilos.p4.ctl.ForgetPasswordCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<%
    String _suc = ServletUtility.getSuccessMessage(request);
    String _err = ServletUtility.getErrorMessage(request);
%>

<div class="container py-5" style="max-width:520px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-4 px-4 text-center"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <div class="d-flex justify-content-center mb-3">
        <div class="rounded-circle d-flex align-items-center justify-content-center"
             style="width:60px;height:60px;background:rgba(255,255,255,0.18);font-size:1.6rem;border:2px solid rgba(255,255,255,0.3);">
          <i class="bi bi-question-circle-fill"></i>
        </div>
      </div>
      <h5 class="mb-1 fw-bold">Forgot Password?</h5>
      <p class="mb-0 opacity-75 small">Enter your email and we'll send your password.</p>
    </div>

    <div class="card-body px-4 py-4">

      <% if(_suc != null && !_suc.isEmpty()) { %>
      <div class="alert alert-success py-2"><i class="bi bi-check-circle-fill me-2"></i><%=_suc%></div>
      <% } %>
      <% if(_err != null && !_err.isEmpty()) { %>
      <div class="alert alert-danger py-2"><i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%></div>
      <% } %>

      <form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="POST">
        <input type="hidden" name="id" value="<%=bean.getId()%>">

        <div class="mb-4">
          <label class="form-label fw-semibold">Email / Login ID <span class="text-danger">*</span></label>
          <input type="text" name="login" class="form-control form-control-lg"
                 placeholder="Enter your registered email"
                 value="<%=ServletUtility.getParameter("login", request)%>">
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("login", request)%></div>
        </div>

        <button type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>"
                class="btn btn-primary btn-lg w-100">
          <i class="bi bi-send me-2"></i> Send Password
        </button>

        <div class="text-center mt-3">
          <a href="<%=ORSView.LOGIN_CTL%>" class="text-secondary small text-decoration-none">
            <i class="bi bi-arrow-left me-1"></i> Back to Login
          </a>
        </div>
      </form>
    </div>
  </div>
</div>
