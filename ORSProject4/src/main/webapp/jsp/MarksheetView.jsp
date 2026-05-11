<%@ taglib uri="http://www.sunilos.com/ors-tags" prefix="ors" %>
<%@page import="com.sunilos.p4.ctl.MarksheetCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="java.util.List"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.MarksheetBean" scope="request"></jsp:useBean>

<div class="container py-4" style="max-width:640px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-3 px-4"
         style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
      <h5 class="mb-0 fw-bold">
        <i class="bi bi-file-earmark-text-fill me-2"></i>
        <%=bean.getId() > 0 ? "Edit Marksheet" : "Add Marksheet"%>
      </h5>
    </div>

    <div class="card-body px-4 py-4">

      <ors:formMsg />

      <form action="<%=ORSView.MARKSHEET_CTL%>" method="POST">
        <input type="hidden" name="id"              value="<%=bean.getId()%>">
        <input type="hidden" name="createdBy"        value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy"       value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime"  value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <div class="mb-3">
          <label class="form-label fw-semibold"><ors:message key="marksheet.rollno"/> <span class="text-danger">*</span></label>
          <input type="text" name="rollNo" class="form-control"
                 value="<%=DataUtility.getStringData(bean.getRollNo())%>"
                 <%=(bean.getId() > 0) ? "readonly" : ""%>>
          <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("rollNo", request)%></div>
        </div>

        <div class="mb-3">
          <label class="form-label fw-semibold"><ors:message key="marksheet.name"/> <span class="text-danger">*</span></label>
          <ors:formSelect name="studentId" value="<%=String.valueOf(bean.getStudentId())%>" list="studentList" />
        </div>

        <div class="row g-3 mb-4">
          <div class="col-md-4">
            <label class="form-label fw-semibold"><ors:message key="marksheet.physics"/></label>
            <input type="text" name="physics" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getPhysics())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("physics", request)%></div>
          </div>
          <div class="col-md-4">
            <label class="form-label fw-semibold"><ors:message key="marksheet.chemistry"/></label>
            <input type="text" name="chemistry" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getChemistry())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("chemistry", request)%></div>
          </div>
          <div class="col-md-4">
            <label class="form-label fw-semibold"><ors:message key="marksheet.maths"/></label>
            <input type="text" name="maths" class="form-control"
                   value="<%=DataUtility.getStringData(bean.getMaths())%>">
            <div class="text-danger small mt-1"><%=ServletUtility.getErrorMessage("maths", request)%></div>
          </div>
        </div>

        <div class="d-flex gap-2 pt-2 border-top">
          <button type="submit" name="operation" value="<%=MarksheetCtl.OP_SAVE%>" class="btn btn-primary">
            <i class="bi bi-save me-1"></i> <ors:message key="button.save"/>
          </button>
          <% if(bean.getId() > 0) { %>
          <button type="submit" name="operation" value="<%=MarksheetCtl.OP_DELETE%>" class="btn btn-danger"
                  onclick="return confirm('Delete this marksheet?')">
            <i class="bi bi-trash me-1"></i> <ors:message key="button.delete"/>
          </button>
          <% } %>
          <a href="MarksheetListCtl?id=0" class="btn btn-secondary ms-auto">
            <i class="bi bi-x-circle me-1"></i> <ors:message key="button.cancel"/>
          </a>
        </div>
      </form>
    </div>
  </div>
</div>
