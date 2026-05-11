<%@ taglib uri="http://www.sunilos.com/ors-tags" prefix="ors" %>
<%@page import="com.sunilos.p4.ctl.LoginCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.util.MessageSource"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<%
    MessageSource ms = MessageSource.getInstance();
%>

<div class="container-fluid min-vh-100 d-flex align-items-center justify-content-center py-5"
     style="background: linear-gradient(135deg, #e8f0fe 0%, #f1f5f9 100%);">

  <div class="col-12 col-sm-9 col-md-6 col-lg-4">

    <!-- Card -->
    <div class="card border-0 shadow-lg rounded-4 overflow-hidden">

      <!-- Card header -->
      <div class="card-header text-white text-center py-4 border-0"
           style="background: linear-gradient(135deg, #0d2137 0%, #1565c0 100%);">
        <div class="d-flex justify-content-center mb-3">
          <div class="rounded-circle d-flex align-items-center justify-content-center"
               style="width:68px; height:68px; background:rgba(255,255,255,0.18); border:2px solid rgba(255,255,255,0.3); font-size:1.9rem;">
            <i class="fas fa-graduation-cap"></i>
          </div>
        </div>
        <h4 class="fw-bold mb-1"><ors:message key="login.title"/></h4>
        <p class="mb-0 opacity-75 small">Online Result System</p>
      </div>

      <!-- Card body -->
      <div class="card-body px-4 py-4">

        <!-- Form-level messages -->
        <ors:formMsg />

        <form action="<%=ORSView.LOGIN_CTL%>" method="POST">

          <!-- Login ID -->
          <div class="mb-3">
            <label class="form-label fw-semibold text-secondary small text-uppercase tracking-wide">
              <i class="bi bi-person-fill me-1"></i><%=ms.get("login.userid")%>
              <span class="text-danger">*</span>
            </label>
            <input type="text"
                   name="login"
                   class="form-control form-control-lg"
                   placeholder="Enter your login ID"
                   value="<%=DataUtility.getStringData(bean.getLogin())%>">
            <% String loginErr = ServletUtility.getErrorMessage("login", request);
               if (loginErr != null && !loginErr.isEmpty()) { %>
            <div class="text-danger small mt-1"><i class="bi bi-exclamation-circle"></i> <%=loginErr%></div>
            <% } %>
          </div>

          <!-- Password -->
          <div class="mb-4">
            <label class="form-label fw-semibold text-secondary small text-uppercase">
              <i class="bi bi-lock-fill me-1"></i><%=ms.get("login.password")%>
              <span class="text-danger">*</span>
            </label>
            <input type="password"
                   name="password"
                   class="form-control form-control-lg"
                   placeholder="Enter your password"
                   value="<%=DataUtility.getStringData(bean.getPassword())%>">
            <% String passErr = ServletUtility.getErrorMessage("password", request);
               if (passErr != null && !passErr.isEmpty()) { %>
            <div class="text-danger small mt-1"><i class="bi bi-exclamation-circle"></i> <%=passErr%></div>
            <% } %>
          </div>

          <!-- Submit -->
          <button type="submit" name="operation"
                  class="btn btn-primary btn-lg w-100 fw-semibold"
                  style="background:linear-gradient(135deg,#1565c0,#0d47a1); border:none;">
            <i class="bi bi-box-arrow-in-right me-2"></i><ors:message key="login.signin"/>
          </button>

          <!-- Footer links -->
          <div class="d-flex justify-content-between align-items-center mt-4 pt-3 border-top">
            <a href="<%=ORSView.USER_REGISTRATION_CTL%>"
               class="text-success fw-semibold text-decoration-none small">
              <i class="bi bi-person-plus-fill me-1"></i><ors:message key="login.signup"/>
            </a>
            <a href="<%=ORSView.FORGET_PASSWORD_CTL%>"
               class="text-secondary text-decoration-none small">
              <i class="bi bi-question-circle me-1"></i><ors:message key="login.forgotpassword"/>
            </a>
          </div>

        </form>
      </div>
    </div>

    <!-- Below card -->
    <p class="text-center text-muted small mt-3">
      &copy; 2024 Rays Technologies &mdash; Online Result System
    </p>

  </div>
</div>
