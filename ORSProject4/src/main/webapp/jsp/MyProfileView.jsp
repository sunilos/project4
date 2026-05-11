<%@page import="com.sunilos.p4.ctl.MyProfileCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
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

    <div class="card-header text-white border-0 py-3 px-4"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <h5 class="mb-0 fw-bold">
        <i class="bi bi-person-circle me-2"></i> My Profile
      </h5>
    </div>

    <div class="card-body px-4 py-4">

      <% if(_suc != null && !_suc.isEmpty()) { %>
      <div class="alert alert-success py-2"><i class="bi bi-check-circle-fill me-2"></i><%=_suc%></div>
      <% } %>
      <% if(_err != null && !_err.isEmpty()) { %>
      <div class="alert alert-danger py-2"><i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%></div>
      <% } %>

      <form action="<%=ORSView.MY_PROFILE_CTL%>" method="POST">
        <input type="hidden" name="id"              value="<%=bean.getId()%>">
        <input type="hidden" name="createdBy"        value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy"       value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime"  value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <div class="mb-3">
          <label class="form-label fw-semibold">Login ID</label>
          <input type="text" name="login" class="form-control bg-light" readonly
                 value="<%=DataUtility.getStringData(bean.getLogin())%>">
        </div>

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

        <div class="row g-3 mb-3">
          <div class="col-md-6">
            <label class="form-label fw-semibold">Gender</label>
            <%=HTMLUtility.getList("gender", bean.getGender(), genderMap)%>
          </div>
          <div class="col-md-6">
            <label class="form-label fw-semibold">Mobile No <span class="text-danger">*</span></label>
            <input type="text" name="mobileNo" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("mobileNo", request)%></div>
          </div>
        </div>

        <div class="mb-4">
          <label class="form-label fw-semibold">Date of Birth (mm/dd/yyyy)</label>
          <div class="input-group">
            <input type="text" name="dob" class="form-control" readonly
                   value="<%=DataUtility.getDateString(bean.getDob())%>">
            <a class="btn btn-outline-secondary" href="javascript:getCalendar(document.forms[0].dob);">
              <img src="../img/cal.jpg" width="16" height="15" alt="Calendar">
            </a>
          </div>
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("dob", request)%></div>
        </div>

        <div class="d-flex gap-2 pt-2 border-top">
          <button type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE%>" class="btn btn-primary">
            <i class="bi bi-save me-1"></i> Save
          </button>
          <button type="submit" name="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>"
                  class="btn btn-outline-warning">
            <i class="bi bi-key me-1"></i> Change Password
          </button>
        </div>
      </form>
    </div>
  </div>
</div>
