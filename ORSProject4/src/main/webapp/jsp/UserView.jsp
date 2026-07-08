<%@page import="com.sunilos.p4.ctl.UserCtl"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<%
    List l = (List) request.getAttribute("roleList");
    String _suc = ServletUtility.getSuccessMessage(request);
    String _err = ServletUtility.getErrorMessage(request);
    HashMap genderMap = new HashMap();
    genderMap.put("M", "Male");
    genderMap.put("F", "Female");
%>

<div class="container py-4" style="max-width:680px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-3 px-4"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <h5 class="mb-0 fw-bold">
        <i class="bi bi-person-fill-add me-2"></i>
        <%=bean.getId() > 0 ? "Edit User" : "Add User"%>
      </h5>
    </div>

    <div class="card-body px-4 py-4">

      <% if(_suc != null && !_suc.isEmpty()) { %>
      <div class="alert alert-success py-2"><i class="bi bi-check-circle-fill me-2"></i><%=_suc%></div>
      <% } %>
      <% if(_err != null && !_err.isEmpty()) { %>
      <div class="alert alert-danger py-2"><i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%></div>
      <% } %>

      <% if(bean.getId() > 0) { %>
      <div class="d-flex align-items-center gap-3 mb-4">
        <img src="<%=ORSView.UPLOAD_PHOTO_CTL%>?id=<%=bean.getId()%>"
             onerror="this.style.display='none';"
             alt="User Photo" class="rounded-circle border" width="80" height="80"
             style="object-fit:cover;">
        <form action="<%=ORSView.UPLOAD_PHOTO_CTL%>" method="POST" enctype="multipart/form-data"
              class="d-flex align-items-center gap-2">
          <input type="hidden" name="id" value="<%=bean.getId()%>">
          <input type="file" name="photo" class="form-control form-control-sm" accept="image/*">
          <button type="submit" class="btn btn-sm btn-outline-primary text-nowrap">
            <i class="bi bi-upload me-1"></i> Upload Photo
          </button>
        </form>
      </div>
      <% } %>

      <form name="userForm" action="<%=ORSView.USER_CTL%>" method="POST">
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
          <label class="form-label fw-semibold">Login ID <span class="text-danger">*</span></label>
          <input type="text" name="login" class="form-control"
                 value="<%=DataUtility.getStringData(bean.getLogin())%>"
                 <%=(bean.getId() > 0) ? "readonly" : ""%>>
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("login", request)%></div>
        </div>

        <% if(bean.getId() <= 0) { %>
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
                   value="<%=DataUtility.getStringData(bean.getPassword())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></div>
          </div>
        </div>
        <% } %>

        <div class="row g-3 mb-3">
          <div class="col-md-6">
            <label class="form-label fw-semibold">Gender</label>
            <%=HTMLUtility.getList("gender", bean.getGender(), genderMap)%>
          </div>
          <div class="col-md-6">
            <label class="form-label fw-semibold">Role</label>
            <%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), l)%>
          </div>
        </div>

        <div class="mb-4">
          <label class="form-label fw-semibold">Date of Birth (mm/dd/yyyy)</label>
          <div class="input-group">
            <input type="text" name="dob" class="form-control" readonly
                   value="<%=DataUtility.getDateString(bean.getDob())%>">
            <a class="btn btn-outline-secondary" href="javascript:getCalendar(document.forms['userForm'].dob);">
              <img src="../img/cal.jpg" width="16" height="15" alt="Calendar">
            </a>
          </div>
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("dob", request)%></div>
        </div>

        <div class="d-flex gap-2 pt-2 border-top">
          <button type="submit" name="operation" value="<%=BaseCtl.OP_SAVE%>" class="btn btn-primary">
            <i class="bi bi-save me-1"></i> Save
          </button>
          <% if(bean.getId() > 0) { %>
          <button type="submit" name="operation" value="<%=BaseCtl.OP_DELETE%>" class="btn btn-danger"
                  onclick="return confirm('Delete this user?')">
            <i class="bi bi-trash me-1"></i> Delete
          </button>
          <% } %>
          <a href="UserListCtl?id=0" class="btn btn-secondary ms-auto">
            <i class="bi bi-x-circle me-1"></i> Cancel
          </a>
        </div>
      </form>
    </div>
  </div>
</div>
