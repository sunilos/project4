<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.util.HTMLUtility"%>
<%@page import="com.rays.pro4.util.DataUtility"%>
<%@page import="com.rays.pro4.util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.UserCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>User Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.bean.UserBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.USER_CTL%>" method="post">

			<%
				List l = (List) request.getAttribute("roleList");
			%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update User </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add User </font></th>
					</tr>
					<%
					}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">First Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name" size="25"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Last Name <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name" size="25"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">LoginId <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="login"
						placeholder="Enter EmailId" size="25"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>

				<%
					if (bean.getId() > 0 && bean != null) {
				%>
				<tr>

					<td><input type="hidden" name="password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td><input type="hidden" name="confirmPassword"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
				</tr>

				<%
					} else {
				%>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Password <span style="color: red">*</span> :
					</th>
					<td><input type="password" name="password"
						placeholder="Enter Password" size="25"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Confirm Password <span style="color: red">*</span>
						:
					</th>
					<td><input type="password" name="confirmPassword"
						placeholder="Re-Enter Password" size="25"
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>
				<%
					}
				%>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Gender <span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String hlist = HTMLUtility.getList("gender", String.valueOf(bean.getGender()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Role <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), l)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Date Of Birth <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dob"
						placeholder="Enter Date Of Birth" size="25" readonly="readonly"
						id="udatee" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Mobile No <span style="color: red">*</span> :
					</th>
					</th>
					<td><input type="text" name="mobileNo"
						placeholder="Enter Mobile No" size="25" maxlength="10"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=UserCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=UserCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=UserCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=UserCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>