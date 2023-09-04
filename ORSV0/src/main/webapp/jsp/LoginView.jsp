<%@page import="com.sunilos.p4.ctl.LoginCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<h1>Login</h1>

<form action="<%=ORSView.LOGIN_CTL%>" method="POST">
  <p class="error-message">
     <%=ServletUtility.getErrorMessage(request)%>
  </p>
  <table>
     <tr>
        <th>LoginId*</th>
        <td>
           <input type="text" name="login" size=30 value="<%=DataUtility.getStringData(bean.getLogin())%>">
           <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
        </td>
     </tr>
     <tr>
        <th>Password*</th>
        <td><input type="password" name="password" size=30 value="<%=DataUtility.getStringData(bean.getPassword())%>">
           <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
        </td>
     </tr>
     <tr>
        <th></th>
        <td colspan="2">
           <input type="submit"  class="primary-btn"  name="operation" value="<%=LoginCtl.OP_SIGN_IN %>"> &nbsp; 
           <input type="submit" class="secondary-btn" name="operation" value="<%=LoginCtl.OP_SIGN_UP %>" > &nbsp;
        </td>
     </tr>
     <tr>
        <th></th>
        <td>
           <a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget my password</b></a>&nbsp;
        </td>
     </tr>
  </table>
</form>
