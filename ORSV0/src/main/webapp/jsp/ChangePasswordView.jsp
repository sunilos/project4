<%@page import="com.sunilos.p4.ctl.ChangePasswordCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<jsp:useBean id="bean" class="com.sunilos.p4.bean.UserBean" scope="request"></jsp:useBean>
<h1>Change Password</h1>
<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="POST">
   <center>
      <table>
         <tr>
            <th>Old Password*</th>
            <td><input type="password" name="oldPassword"
               value=<%=DataUtility
                  .getString(request.getParameter("oldPassword") == null ? ""
                  		: DataUtility.getString(request
                  				.getParameter("oldPassword")))%>><font
               color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
         </tr>
         <tr>
            <th>New Password*</th>
            <td><input type="password" name="newPassword"
               value=<%=DataUtility
                  .getString(request.getParameter("newPassword") == null ? ""
                  		: DataUtility.getString(request
                  				.getParameter("newPassword")))%>><font
               color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
         </tr>
         <tr>
            <th>Confirm Password*</th>
            <td><input type="password" name="confirmPassword"
               value=<%=DataUtility.getString(request
                  .getParameter("confirmPassword") == null ? "" : DataUtility
                  .getString(request.getParameter("confirmPassword")))%>><font
               color="red"> <%=ServletUtility
               .getErrorMessage("confirmPassword", request)%></font></td>
         </tr>
         <tr>
            <th></th>
            <td colspan="2">
               <input type="submit" class="primary-btn" name="operation" value="<%= ChangePasswordCtl.OP_SAVE%>"> &nbsp;
            </td>
         </tr>
      </table>
      <p class="success-message">
         <%=ServletUtility.getSuccessMessage(request)%>
      </p>
      <p class="error-message">
         <%=ServletUtility.getErrorMessage(request)%>
      </p>
   </center>
</form>