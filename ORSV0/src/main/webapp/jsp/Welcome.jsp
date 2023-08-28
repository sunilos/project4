<%@page import="com.sunilos.p4.ctl.ORSView"%>
<html>
<head>
	<link rel="icon" href="img/favicon.ico" type="image/x-icon">
</head>
<body>
	<form action="<%=ORSView.WELCOME_CTL%>">
		<%@ include file="Header.jsp"%>
					<h1 align="Center">
						<font size="10px" color="red">Welcome to ORS </font>
					</h1>
		
	            	<%
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