
<%@page import="com.sunilos.p4.ctl.GetMarksheetCtl"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<html>
<body>
	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="com.sunilos.p4.bean.MarksheetBean"
		scope="request"></jsp:useBean>

	<center>
		<h1>Get Marksheet</h1>

		<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font>

		<H2>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</H2>

		<form action="<%=ORSView.GET_MARKSHEET_CTL%>">

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table>
				<label>RollNo :</label>&emsp;
				<input type="text" name="rollNo"
					value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;
				<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>">
				<br>
				<font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font>

				<%
					if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {
				%>

				<tr>
					<td>Rollno :</td>
					<td><%=DataUtility.getStringData(bean.getRollNo())%></td>
				</tr>
				<tr>
					<td>Name :</td>
					<td><%=DataUtility.getStringData(bean.getName())%></td>
				</tr>
				<tr>
					<td>Physics :</td>
					<td><%=DataUtility.getStringData(bean.getPhysics())%></td>
				</tr>
				<tr>
					<td>Chemistry :</td>
					<td><%=DataUtility.getStringData(bean.getChemistry())%></td>
				</tr>
				<tr>
					<td>Maths :</td>
					<td><%=DataUtility.getStringData(bean.getMaths())%></td>

				</tr>
				<tr>
					</td>
				</tr>
			</table>
			<%
				}
			%>
		</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>