<%@page import="com.sunilos.p4.ctl.UserCtl"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="java.util.List"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean"
   scope="request"></jsp:useBean>
<%
   List l = (List) request.getAttribute("roleList");
   %>

<h1>Add User</h1>

<form action="<%=ORSView.USER_CTL%>" method="POST">

   <input type="hidden" name="id" value="<%=bean.getId()%>">
   <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
   <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
   <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
   <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

   <center>

      <p class="success-message">
         <%=ServletUtility.getSuccessMessage(request)%>
      </p>
      <p class="error-message">
         <%=ServletUtility.getErrorMessage(request)%>
      </p>

      <table>
         <tr>
            <th>First Name*</th>
            <td><input type="text" name="firstName"
               value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
         </tr>
         <tr>
            <th>Last Name*</th>
            <td><input type="text" name="lastName"
               value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
         </tr>
         <tr>
            <th>LoginId*</th>
            <td><input type="text" name="login"
               value="<%=DataUtility.getStringData(bean.getLogin())%>"
               <%=(bean.getId() > 0) ? "readonly" : ""%>><font
               color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
         </tr>
         <tr>
            <th>Password*</th>
            <td><input type="password" name="password"
               value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
         </tr>
         <tr>
            <th>Confirm Password*</th>
            <td><input type="password" name="confirmPassword"
               value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("confirmPassword",
               request)%></font></td>
         </tr>
         <tr>
            <th>Gender</th>
            <td>
               <%
                  HashMap map = new HashMap();
                  map.put("M", "Male");
                  map.put("F", "Female");
                  
                  String htmlList = HTMLUtility.getList("gender", bean.getGender(),
                  		map);
                  %> <%=htmlList%>
               <b>Role :</b>
               <%=HTMLUtility.getList("roleId",
                  String.valueOf(bean.getRoleId()), l)%>
            </td>
         </tr>
         <tr>
            <th>Date Of Birth (mm/dd/yyyy)</th>
            <td><input type="text" name="dob" readonly="readonly"
               value="<%=DataUtility.getDateString(bean.getDob())%>">
               <a href="javascript:getCalendar(document.forms[0].dob);">
               <img src="../img/cal.jpg" width="16" height="15" border="0"
                  alt="Calender">
               </a><font
                  color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
            </td>
         </tr>
         <tr>
            <th></th>
            <td colspan="2"><input class="primary-btn" type="submit" name="operation"
               value="<%=BaseCtl.OP_SAVE%>"> <%
               if (bean.getId() > 0) {
               %> &emsp;<input class="danger-btn" type="submit" name="operation"
               value="<%=BaseCtl.OP_DELETE%>"> <%
               }
               %>&emsp; <a class="secondary-btn" href="UserListCtl?id=0" >Cancel</a></td>
         </tr>
      </table>
   </center>
</form>