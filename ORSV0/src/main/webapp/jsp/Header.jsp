<%@page import="com.sunilos.p4.bean.RoleBean"%>
<%@page import="com.sunilos.p4.ctl.LoginCtl"%>
<%@page import="com.sunilos.p4.bean.UserBean"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%
   UserBean userBean = (UserBean) session.getAttribute("user");
   
   boolean userLoggedIn = userBean != null;
   
   String welcomeMsg = "Hi, ";
   
   if (userLoggedIn) {
   	String role = (String) session.getAttribute("role");
   	welcomeMsg += userBean.getFirstName() + " (" + role + ")";
   } else {
   	welcomeMsg += "Guest";
   }
   %>
<table width="100%" border="0">
   <tr>
      <td width="90%" ><a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a> |
         <%
            if (userLoggedIn) {
            %> <a href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>
         <%
            } else {
            %> <a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> <%
            }
            %>
      </td>
      <td rowspan="2">
         <h1 align="Right">
            <img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.jpg" width="318"
               height="90">
         </h1>
      </td>
   </tr>
   <tr>
      <td >
         <h3>
            <%=welcomeMsg%>
         </h3>
      </td>
   </tr>
   <%
      if (userLoggedIn) {
      %>
   <tr>
      <td colspan="2" >
         <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |
         <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet Merit
         List</b>
         </a> | <a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> | <a
            href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> | <a
            href="<%=ORSView.JAVA_DOC_VIEW%>">Java Doc</b></a> | <%
            if (userBean.getRoleId() == RoleBean.ADMIN) {
            %> <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> | <a
            href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | 
         <a href="<%=ORSView.USER_CTL%>">Add User</b></a> | <a
            href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> | <a
            href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> | <a
            href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | <a
            href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
            href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | <a
            href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> | <a
            href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> | | <%
            }
            %>
      </td>
   </tr>
   <%
      }
      %>
</table>
<hr>