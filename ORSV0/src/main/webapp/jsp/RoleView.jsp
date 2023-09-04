<%@page import="com.sunilos.p4.ctl.RoleCtl"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.RoleBean" scope="request"></jsp:useBean>
<h1>Add Role</h1>

<form action="<%=ORSView.ROLE_CTL%>" method="POST">
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
         <th>Name*</th>
         <td><input type="text" name="name"
            value="<%=DataUtility.getStringData(bean.getName())%>"><font
            color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
      </tr>
      <tr>
         <th>Description*</th>
         <td><input type="text" name="description"
            value="<%=DataUtility.getStringData(bean.getDescription())%>"><font
            color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
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
            <a class="secondary-btn" href="<%=ORSView.ROLE_LIST_CTL%>" >Cancel</a>
         </td>
      </tr>      
   </table>
</form>