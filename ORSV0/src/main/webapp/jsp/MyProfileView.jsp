<%@page import="com.sunilos.p4.ctl.MyProfileCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<h1>My Profile</h1>

<form action="<%=ORSView.MY_PROFILE_CTL%>" method="POST">

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
            <th>LoginId*</th>
            <td><input type="text" name="login"
               value="<%=DataUtility.getStringData(bean.getLogin())%>"readonly="readonly"><font
               color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
         </tr>
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
            <th>Gender</th>
            <td>
               <%
                  HashMap map = new HashMap();
                  map.put("M", "Male");
                  map.put("F", "Female");
                  
                  String htmlList = HTMLUtility.getList("gender", bean.getGender(),
                  		map);
                  %> <%=htmlList%>
            </td>
         </tr>
         <tr>
            <th>Mobile No*</th>
            <td><input type="text" name="mobileNo"
               value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
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
            <td colspan="2">
               <input type="submit"  class="primary-btn" name="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD %>"> &nbsp; 
               <input type="submit"  class="primary-btn" name="operation" value="<%=MyProfileCtl.OP_SAVE %>"> &nbsp;
            </td>
         </tr>
      </table>
   </center>
</form>