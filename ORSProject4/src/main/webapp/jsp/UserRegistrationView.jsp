<%@page import="com.sunilos.p4.ctl.UserRegistrationCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<%
    String _suc = ServletUtility.getSuccessMessage(request);
    String _err = ServletUtility.getErrorMessage(request);
    HashMap genderMap = new HashMap();
    genderMap.put("M", "Male");
    genderMap.put("F", "Female");
%>

<div class="container py-4" style="max-width:660px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-4 px-4 text-center"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <div class="d-flex justify-content-center mb-3">
        <div class="rounded-circle d-flex align-items-center justify-content-center"
             style="width:60px;height:60px;background:rgba(255,255,255,0.18);font-size:1.6rem;border:2px solid rgba(255,255,255,0.3);">
          <i class="bi bi-person-plus-fill"></i>
        </div>
      </div>
      <h5 class="mb-0 fw-bold">User Registration</h5>
      <p class="mb-0 opacity-75 small mt-1">Create your ORS account</p>
    </div>

    <div class="card-body px-4 py-4">

      <% if(_suc != null && !_suc.isEmpty()) { %>
      <div class="alert alert-success py-2"><i class="bi bi-check-circle-fill me-2"></i><%=_suc%></div>
      <% } %>
      <% if(_err != null && !_err.isEmpty()) { %>
      <div class="alert alert-danger py-2"><i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%></div>
      <% } %>

      <form method="POST">
        <input type="hidden" name="id"              value="<%=bean.getId()%>">
        <input type="hidden" name="createdBy"        value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy"       value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime"  value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <div class="row g-3 mb-3">
          <div class="col-md-6">
            <label class="form-label fw-semibold">First Name <span class="text-danger">*</span></label>
            <input type="text" name="firstName" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getFirstName())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("firstName", request)%></div>
          </div>
          <div class="col-md-6">
            <label class="form-label fw-semibold">Last Name <span class="text-danger">*</span></label>
            <input type="text" name="lastName" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getLastName())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("lastName", request)%></div>
          </div>
        </div>

        <div class="mb-3">
          <label class="form-label fw-semibold">Login ID (Email) <span class="text-danger">*</span></label>
          <input type="text" name="login" class="form-control" placeholder="Must be Email ID"
                 value="<%=DataUtility.getStringData(bean.getLogin())%>">
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("login", request)%></div>
        </div>

        <div class="row g-3 mb-3">
          <div class="col-md-6">
            <label class="form-label fw-semibold">Password <span class="text-danger">*</span></label>
            <input type="password" name="password" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getPassword())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("password", request)%></div>
          </div>
          <div class="col-md-6">
            <label class="form-label fw-semibold">Confirm Password <span class="text-danger">*</span></label>
            <input type="password" name="confirmPassword" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></div>
          </div>
        </div>

        <div class="row g-3 mb-3">
          <div class="col-md-6">
            <label class="form-label fw-semibold">Gender</label>
            <%=HTMLUtility.getList("gender", bean.getGender(), genderMap)%>
          </div>
          <div class="col-md-6">
            <label class="form-label fw-semibold">Date of Birth (mm/dd/yyyy)</label>
            <div class="input-group">
              <input type="text" name="dob" class="form-control" readonly
                     value="<%=DataUtility.getDateString(bean.getDob())%>">
              <a class="btn btn-outline-secondary" href="javascript:getCalendar(document.forms[0].dob);">
                <img src="./img/cal.jpg" width="16" height="15" alt="Calendar">
              </a>
            </div>
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("dob", request)%></div>
          </div>
        </div>

        <div class="pt-3 border-top">
          <% if(bean.getId() <= 0) { %>
          <button type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP%>"
                  class="btn btn-primary btn-lg w-100">
            <i class="bi bi-person-check-fill me-2"></i> Sign Up
          </button>
          <% } else { %>
          <a href="<%=ORSView.LOGIN_CTL%>" class="btn btn-outline-primary btn-lg w-100">
            <i class="bi bi-box-arrow-in-right me-2"></i> Go to Login
          </a>
          <% } %>
          <p class="text-center mt-3 mb-0 small text-muted">
            Already have an account?
            <a href="<%=ORSView.LOGIN_CTL%>" class="text-primary fw-semibold">Login here</a>
          </p>
        </div>
      </form>
    </div>
  </div>
</div>
