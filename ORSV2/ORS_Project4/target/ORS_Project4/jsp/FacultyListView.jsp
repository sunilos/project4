<%@page import="com.rays.pro4.Model.FacultyModel"%>
<%@page import="com.rays.pro4.Bean.FacultyBean"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.controller.FacultyListCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Faculty List</title>

<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/Checkbox11.js"></script>


</head>
<body>
<%@include file ="Header.jsp"%>

<jsp:useBean id="bean" class="com.rays.pro4.Bean.FacultyBean" scope="request"></jsp:useBean>
<jsp:useBean id="colbean" class="com.rays.pro4.Bean.CollegeBean" scope="request"></jsp:useBean>
<jsp:useBean id="corbean" class="com.rays.pro4.Bean.CourseBean" scope="request"></jsp:useBean>


<center>
<form action="<%=ORSView.FACULTY_LIST_CTL %>" method="post">
	
	<div align="center">
			<h1>Faculty List</h1>
			<h3><font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font> </h3>
			<h3><font style="color: red"><%=ServletUtility.getErrorMessage(request) %></font> </h3>
	</div>
	
	
	   <% 
	             List clist=(List)request.getAttribute("CollegeList");
	              
	              List colist=(List)request.getAttribute("CourseList");
	              
	              int next=DataUtility.getInt(request.getAttribute("nextlist").toString());

	    		%>
	    
	
	
	<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	
	int index=(pageNo-1)*pageSize+1;
	List list = ServletUtility.getList(request);
	Iterator<FacultyBean> it = list.iterator();
	
		if(list.size() !=0){
	%>
	
	<table width ="100%" align="center">
		<tr><th></th>
		<td align="center">
		<label>First Name :</label>
		<input type="text" name="firstname" placeholder="Enter First Name" value=<%=ServletUtility.getParameter("firstname", request) %>>
		&nbsp;
		<label>College Name :</label>
		 <%=HTMLUtility.getList("collegeid", String.valueOf(bean.getCollegeId()), clist) %>
                   	
		&nbsp;
		<label>Course Name :</label>
		<%=HTMLUtility.getList("courseid", String.valueOf(bean.getCourseId()), colist) %>		
		&nbsp;
		
		<input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH%>">
		
		<input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET %>">
		</td></tr>
	</table>
	<br>	
	<table border="1" width="100%" align="center" cellpadding=8px cellspacing=".2">
		
             <tr style="background: skyblue">
		
			<th ><input type="checkbox" id="select_all" name ="Select">Select All.</th>
			
			<th>S.No.</th>
			<th>First Name.</th>
			<th>Last Name.</th>
			<th>EmailId.</th>
			<th>College Name.</th>
			<th>Course Name.</th>
			<th>Subject Name.</th>
			<th>DOB.</th>
			<th>Mobile No.</th>
			<th>Edit</th>
		</tr>
		
		<%
		while(it.hasNext()){
	 	bean =it.next();	
		%>
		
	<tr>
		<td><input type = "checkbox" class="checkbox" name="ids" value="<%=bean.getId() %>"></td>
		<td><%=index++%></td>
		<td><%=bean.getFirstName() %></td>
		<td><%=bean.getLastName() %></td>
		<td><%=bean.getEmailId() %></td>
		<td><%=bean.getCollegeName() %></td>
		<td><%=bean.getCourseName() %></td>
		<td><%=bean.getSubjectName() %></td>
		<td><%=bean.getDob() %></td>
		<td><%=bean.getMobileNo() %></td>
		<td><a href="FacultyCtl?id=<%=bean.getId()%>">Edit </a></td>
	
	</tr>
		<% 
		}
		%>
	</table>

	<table width = "100%">
		<tr><th></th>
		<% if(pageNo == 1){ %>
		<td align="left" ><input type="submit" name="operation" disabled="disabled" value="<%=FacultyListCtl.OP_PREVIOUS %>"></td>
		 <%}else{ %>
		 <td align="left" ><input type="submit" name="operation" value="<%=FacultyListCtl.OP_PREVIOUS %>"></td>
		 <%} %>
			<td><input type="submit" name="operation" value="<%=FacultyListCtl.OP_DELETE%>"></td>
			<td><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>"></td>	
			
			<%-- <%
				FacultyModel model = new FacultyModel();
	
			%>		
		<% if(list.size() < pageSize || model.nextPk() - 1 == bean.getId()) { %>		
		<td align="right"><input type="submit" name="operation" disabled="disabled" value="<%=FacultyListCtl.OP_NEXT %>"></td>
		<%}else{ %>
				<td align="right"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEXT %>"></td>
		<%} %>
 --%>	
                    <td align="right"><input type="submit"  name="operation" value="<%=FacultyListCtl.OP_NEXT%>" <%=(list.size()<pageSize||next==0)?"disabled":"" %>> </td>
			        
                 
 	</tr>
	</table>
	
			<%}if(list.size() == 0){ %>
            		<td align="center"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_BACK%>"></td>	
            		<% } %>
            		
	
		<input type="hidden" name="pageNo" value="<%=pageNo %>">
		<input type="hidden" name="pageSize" value="<%=pageSize%>">
</form>
</br>
</br>
                   </br>
                   </br>
                   </br>
</br>
</center>

<%@include file = "Footer.jsp" %>
</body>
</html>