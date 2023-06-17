
<%@page import="in.co.sunrays.proj4.controller.CollegeCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<html>
<body>
	<form action="CollegeCtl">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CollegeBean"
			scope="request"></jsp:useBean>

		<center>
			<h1>Add College</h1>

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th>Name*</th>
					<td><input type="text" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th>Address*</th>
					<td><input type="text" name="address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
				</tr>
				<tr>
					<th>State*</th>
					<td><input type="text" name="state"
						value="<%=DataUtility.getStringData(bean.getState())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
				</tr>
				<tr>
					<th>City*</th>
					<td><input type="text" name="city"
						value="<%=DataUtility.getStringData(bean.getCity())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
				</tr>
				<tr>
					<th>PhoneNo*</th>
					<td><input type="text" name="phoneNo"
						value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
				</tr>


				<tr> 
				    <th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=CollegeCtl.OP_SAVE%>">
						<%
				 	if (bean.getId() > 0) {
				 %> &emsp;<input type="submit" name="operation"
						value="<%=CollegeCtl.OP_DELETE%>"> <%
				 	}
				 %>&emsp; <input type="submit" name="operation"
						value="<%=CollegeCtl.OP_CANCEL%>"></td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>