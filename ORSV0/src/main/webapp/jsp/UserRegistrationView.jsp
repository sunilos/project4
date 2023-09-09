<%@page import="com.sunilos.p4.ctl.UserRegistrationCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<h1>User Registration</h1>

<form method="POST">
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
            placeholder="Must be Email ID"
            value="<%=DataUtility.getStringData(bean.getLogin())%>"><font
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
            value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"><font
            color="red"> <%=ServletUtility
            .getErrorMessage("confirmPassword", request)%></font></td>
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
         <th>Date Of Birth (mm/dd/yyyy)</th>
         <td><input type="text" name="dob" readonly="readonly"
            value="<%=DataUtility.getDateString(bean.getDob())%>"> <a
            href="javascript:getCalendar(document.forms[0].dob);"> <img
            src="./img/cal.jpg" width="16" height="15" border="0"
            alt="Calender">
            </a><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
         </td>
      </tr>
      <tr>
         <th></th>
         <td colspan="2" align="center">
         	<%
         	long id = bean.getId();
         	if (id <= 0){
         	%>
         		<input  class="primary-btn" type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP %>">
         	<%
         	}else{
         	%>
 				<a class="secondary-btn" href="<%=ORSView.LOGIN_CTL%>" >Login</a>
 			<%
 			}
 			%>
         </td>
      </tr>
   </table>

</form>