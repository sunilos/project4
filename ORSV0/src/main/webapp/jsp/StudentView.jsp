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
<h1>Add Student</h1>
<form action="StudentCtl" method="POST">
   <input type="hidden" name="id" value="<%=bean.getId()%>">
   <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
   <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
   <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
   <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
   <p class="success-message">
      <%=ServletUtility.getSuccessMessage(request)%>
   </p>
   <p class="error-message">
      <%=ServletUtility.getErrorMessage(request)%>
   </p>
   <table>
      <tr>
         <th>College*</th>
         <td><%=HTMLUtility.getList("collegeId",
            String.valueOf(bean.getCollegeId()), l)%></td>
      </tr>
      <tr>
         <th>First Name*</th>
         <td><input type="text" name="firstName"
            value="<%=DataUtility.getStringData(bean.getFirstName())%>"
            <%=(bean.getId() > 0) ? "readonly" : ""%>><font
            color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
      </tr>
      <tr>
         <th>Last Name*</th>
         <td><input type="text" name="lastName"
            value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
            color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
      </tr>
      <tr>
         <th>Date Of Birth (mm/dd/yyyy)</th>
         <td><input type="text" name="dob" readonly="readonly"
            value="<%=DataUtility.getDateString(bean.getDob())%>"> <a
            href="javascript:getCalendar(document.forms[0].dob);"> <img
            src="../img/cal.jpg" width="16" height="15" border="0"
            alt="Calender">
            </a><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
         </td>
      </tr>
      <tr>
         <th>MobileNo*</th>
         <td><input type="text" name="mobileNo"
            value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
            color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
      </tr>
      <tr>
         <th>Email ID*</th>
         <td><input type="text" name="email"
            value="<%=DataUtility.getStringData(bean.getEmail())%>"><font
            color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
      </tr>
      <tr>
         <th></th>
         <td colspan="2">
            <input class="primary-btn" type="submit" name="operation" value="<%=BaseCtl.OP_SAVE%>"> &nbsp;
            <%
               if (bean.getId() > 0) {
               %> 
            <input class="danger-btn" type="submit" name="operation"  value="<%=BaseCtl.OP_DELETE%>"> &nbsp;
            <%
               }
               %>
            <a class="secondary-btn" href="<%=ORSView.STUDENT_LIST_CTL%>" >Cancel</a>
         </td>
      </tr>
   </table>
</form>