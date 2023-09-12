<%@page import="com.sunilos.p4.ctl.ForgetPasswordCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>

<h1>Forgot your password?</h1>

<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="POST">

   <input type="hidden" name="id" value="<%=bean.getId()%>">

   <table>
      <b> Submit your email address and we'll send you password. </b>
      <br><br>
      <label>Email Id :</label>&emsp;
      <input type="text" name="login" placeholder="Enter ID Here"  value="<%=ServletUtility.getParameter("login", request)%>">&emsp;
      <input class="primary-btn" type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>"><br>
      <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
   </table>

   <p class="success-message">
      <%=ServletUtility.getSuccessMessage(request)%>
   </p>
   <p class="error-message">
      <%=ServletUtility.getErrorMessage(request)%>
   </p>
</form>