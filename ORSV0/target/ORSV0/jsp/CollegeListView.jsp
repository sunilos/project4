<%@page import="com.sunilos.p4.ctl.CollegeListCtl"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<body>
	<%@include file="Header.jsp"%>
	<center>
		<h1>College List</h1>

		<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST" >

			<table width="100%">
				<tr>
					<td align="center"><label> Name :</label> <input type="text"
						name="name"
						value="<%=ServletUtility.getParameter("name", request)%>">
						<label>City :</label> <input type="text" name="city"
						value="<%=ServletUtility.getParameter("city", request)%>">
						<input type="submit" name="operation" value="<%=CollegeListCtl.OP_SEARCH %>">&nbsp;
						<a href="CollegeCtl">Add</a>
					</td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<th>S.No.</th>
					<th>ID.</th>
					<th>Name.</th>
					<th>Address.</th>
					<th>State.</th>
					<th>City.</th>
					<th>PhoneNo.</th>
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
					Iterator<CollegeBean> it = list.iterator();

					while (it.hasNext()) {

						CollegeBean bean = it.next();
				%>
				<tr>
					<td><%=index++%></td>
					<td><%=bean.getId()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getAddress()%></td>
					<td><%=bean.getState()%></td>
					<td><%=bean.getCity()%></td>
					<td><%=bean.getPhoneNo()%></td>
					<td><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_PREVIOUS%>"></td>
					<td></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_NEXT%>"></td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</center>
</body>
</html>