<%@page import="com.sunilos.p4.ctl.LoginCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.util.MessageSource"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>


<%
	MessageSource ms = (MessageSource)application.getAttribute("messagesource");
%>

<h1><%=ms.get("login.title")%></h1>

<form action="<%=ORSView.LOGIN_CTL%>" method="POST">
  <p class="error-message">
     <%=ServletUtility.getErrorMessage(request)%>
  </p>
  <table>
     <tr>
        <th><%=ms.get("login.userid")%> <font color="red">*</font></th>
        <td>
           <input type="text" name="login" size=30 value="<%=DataUtility.getStringData(bean.getLogin())%>">
           <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
        </td>
     </tr>
     <tr>
        <th><%=ms.get("login.password")%> <font color="red">*</font></th>
        <td><input type="password" name="password" size=30 value="<%=DataUtility.getStringData(bean.getPassword())%>">
           <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
        </td>
     </tr>
     <tr>
        <th></th>
        <td colspan="2">
           <button class="primary-btn" onClick="this.form.submit()" ><%=ms.get("login.signin")%></button> &nbsp; 
           <a class="success-btn" href="<%=ORSView.USER_REGISTRATION_CTL%>"><%=ms.get("login.signup")%></a>&nbsp;
        </td>
     </tr>
     <tr>
        <th></th>
        <td>
           <a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b><%=ms.get("login.forgotpassword")%></b></a>&nbsp;
        </td>
     </tr>
  </table>
</form>
