 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.rays.pro4.controller.LoginCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<form action="<%=ORSView.LOGIN_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.rays.pro4.Bean.UserBean"
			scope="request"></jsp:useBean>
	
		<%
			String uri = (String) request.getAttribute("URI");
			/* System.out.print("Uri on jsp " + uri); */
		%>
		<input type="hidden" name="URI" value="<%=uri%>">
		<center>
			<h1>Login</h1>

			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<h2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th>LoginId <font color="red">*</font></th>
					<td><input type="text" name="login" size=30
						placeholder="Enter valid Email-Id"
						 value="<%=DataUtility.getStringData(bean.getLogin())%>"><font
						style="position: fixed" color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
								<tr>
					<th>Password<font color="red">*</font></th>
					<td><input type="password" name="password" size=30
						placeholder="Enter Password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>">
						<font style="position: fixed" color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_IN%>"> &nbsp; <input
						type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>">
						&nbsp;</td>
				</tr>
				<tr>
					<th></th>
					<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget
								my password</b></a>&nbsp;</td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>


</body>
</html>