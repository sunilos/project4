
<%@page import="in.co.sunrays.proj4.controller.UserListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<body>

	<%@include file="Header.jsp"%>

	<center>
		<h1>User List</h1>

		<form action="<%=ORSView.USER_LIST_CTL%>">

			<table width="100%">
				<tr>
					<td align="center"><label>FirstName :</label> <input
						type="text" name="firstName"
						value="<%=ServletUtility.getParameter("firstName", request)%>">
						&emsp; <label>LoginId:</label> <input type="text" name="login"
						value="<%=ServletUtility.getParameter("login", request)%>">
						&emsp; <input type="submit" name="operation" value="<%=UserListCtl.OP_SEARCH %>">
					</td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%">
				<tr>
					<th>Select</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>DOB</th>
					<th>Edit</th>
				</tr>

				<tr>
					<td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
				</tr>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<UserBean> it = list.iterator();
					while (it.hasNext()) {
						UserBean bean = it.next();
				%>
				<tr>
					<td><input type="checkbox" name="ids" value="<%=bean.getId()%>"></td>
					<td><%=bean.getFirstName()%></td>
					<td><%=bean.getLastName()%></td>
					<td><%=bean.getLogin()%></td>
					<td><%=bean.getGender()%></td>
					<td><%=bean.getDob()%></td>
					<td><a href="UserCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<td ><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					 <td ><input type="submit"
						name="operation" value="<%=UserListCtl.OP_DELETE%>"></td>
					 <td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"></td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>