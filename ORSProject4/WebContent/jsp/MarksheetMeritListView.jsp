<%@page import="in.co.sunrays.proj4.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<body>
	<%@include file="Header.jsp"%>
	<center>
		<h1>Marksheet Merit List</h1>

		<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">
			<br>
			<table border="1" width="100%">
				<tr>

					<th>ID</th>
					<th>Roll No</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>

				</tr>
				<tr>
					<td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
				</tr>
				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<MarksheetBean> it = list.iterator();

					while (it.hasNext()) {

						MarksheetBean bean = it.next();
				%>
				<tr>

					<td><%=index++%></td>
					<td><%=bean.getRollNo()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getPhysics()%></td>
					<td><%=bean.getChemistry()%></td>
					<td><%=bean.getMaths()%></td>

				</tr>

				<%
					}
				%>
			</table>
			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>