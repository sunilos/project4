<%@ taglib uri="http://www.sunilos.com/ors-tags" prefix="ors" %>
<%@page import="com.sunilos.p4.ctl.StudentCtl"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="java.util.List"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.StudentBean" scope="request"></jsp:useBean>

<%
    List l = (List) request.getAttribute("collegeList");
%>

<div class="container py-4" style="max-width:680px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-3 px-4"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <h5 class="mb-0 fw-bold">
        <i class="bi bi-mortarboard-fill me-2"></i>
        <%=bean.getId() > 0 ? "Edit Student" : "Add Student"%>
      </h5>
    </div>

    <div class="card-body px-4 py-4">

      <ors:formMsg />

      <form action="StudentCtl" method="POST">
        <input type="hidden" name="id"              value="<%=bean.getId()%>">
        <input type="hidden" name="createdBy"        value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy"       value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime"  value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <div class="mb-3">
          <label class="form-label fw-semibold">College <span class="text-danger">*</span></label>
          <%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), l)%>
        </div>

        <div class="row g-3 mb-3">
          <div class="col-md-6">
            <label class="form-label fw-semibold">First Name <span class="text-danger">*</span></label>
            <input type="text" name="firstName" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getFirstName())%>"
                   <%=(bean.getId() > 0) ? "readonly" : ""%>>
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

        <div class="mb-3">
          <label class="form-label fw-semibold">Mobile No <span class="text-danger">*</span></label>
          <input type="text" name="mobileNo" class="form-control"
                 value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("mobileNo", request)%></div>
        </div>

        <div class="mb-4">
          <label class="form-label fw-semibold">Email ID <span class="text-danger">*</span></label>
          <input type="text" name="email" class="form-control"
                 value="<%=DataUtility.getStringData(bean.getEmail())%>">
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("email", request)%></div>
        </div>

        <div class="d-flex gap-2 pt-2 border-top">
          <button type="submit" name="operation" value="<%=BaseCtl.OP_SAVE%>" class="btn btn-primary">
            <i class="bi bi-save me-1"></i> Save
          </button>
          <% if(bean.getId() > 0) { %>
          <button type="submit" name="operation" value="<%=BaseCtl.OP_DELETE%>" class="btn btn-danger"
                  onclick="return confirm('Delete this student?')">
            <i class="bi bi-trash me-1"></i> Delete
          </button>
          <% } %>
          <a href="<%=ORSView.STUDENT_LIST_CTL%>" class="btn btn-secondary ms-auto">
            <i class="bi bi-x-circle me-1"></i> Cancel
          </a>
        </div>
      </form>
    </div>
  </div>
</div>
