<%@page import="com.rays.pro4.Bean.RoleBean"%>
<%@page import="com.rays.pro4.Bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.rays.pro4.controller.ORSView"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome Page</title>
</head>
<body>

	<form action="<%=ORSView.WELCOME_CTL%>">
	<%@ include file="Header.jsp"%>
		<h1 align="Center">
			<font size="10px" color="red">Welcome to ORS </font>
		</h1>

		<%
		System.out.println("WelcomeView");
			UserBean beanUserBean = (UserBean) session.getAttribute("user");
			if (beanUserBean != null) {
				if (beanUserBean.getRoleId() == RoleBean.STUDENT) {
		%>

		<h2 align="Center">
			<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Click here to see your
				Marksheet </a>
		</h2>

		<%
			}
			}
		%>

	</form>

	<%@ include file="Footer.jsp"%> 
</body>
</html>