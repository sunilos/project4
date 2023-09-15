<%@page import="com.sunilos.p4.ctl.LoginCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@ taglib uri="http://www.sunilos.com/ors-tags" prefix="ors" %>

<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.util.MessageSource"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<%
	MessageSource ms = (MessageSource)application.getAttribute("messagesource");
%>

<h1><ors:message key="login.title"/></h1>

  <form action="<%=ORSView.LOGIN_CTL%>" method="POST">
  
  <ors:formMsg />
  
  <table>
     <tr>
        <th><%=ms.get("login.userid")%><font color="red">*</font></th>
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
           <button class="primary-btn" onClick="this.form.submit()" ><ors:message key="login.signin"/></button> &nbsp; 
           <a class="success-btn" href="<%=ORSView.USER_REGISTRATION_CTL%>"><ors:message key="login.signup"/></a>&nbsp;
        </td>
     </tr>
     <tr>
        <th></th>
        <td>
           <a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b><ors:message key="login.forgotpassword"/></b></a>&nbsp;
        </td>
     </tr>
  </table>
</form>
