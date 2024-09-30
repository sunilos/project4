<%@page import="com.rays.pro4.Model.StudentModel"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rays.pro4.Bean.StudentBean"%>
<%@page import="com.rays.pro4.controller.StudentListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Student List</title>

<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/Checkbox11.js"></script>


</head>
<body>
<jsp:useBean id="cbean" class="com.rays.pro4.Bean.CollegeBean" scope="request" ></jsp:useBean>
<jsp:useBean id="bean" class="com.rays.pro4.Bean.StudentBean" scope="request" ></jsp:useBean>

  <form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
    <%@include file="Header.jsp"%>
   
    <center>
    
     <div align="center">
	        <h1>Student List</h1>
            <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
            <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
     </div>
     
     <% 
         List clist=(List) request.getAttribute("CollegeList");
     %>
     
       <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<StudentBean> it = list.iterator();

	       			if(list.size() !=0){
                    %>
            
            <table width="100%" align="center">
                 <tr>
                 <td align="right">
                 <label> First Name:</label> 
                 	<input type="text" name="firstName" placeholder="Enter Student Name" Size= "22" value="<%=ServletUtility.getParameter("firstName", request)%>">
				<!-- 	&nbsp;
                 --> 	<label>Last Name:</label> 
    	             <input type="text" name="lastName" placeholder="Enter last Name" Size= "22" value="<%=ServletUtility.getParameter("lastName", request)%>">
                <!--     &nbsp;
 -->                 	<label>EmailId:</label> 
    	             <input type="text" name="email" placeholder="Enter Email_id" Size= "22" value="<%=ServletUtility.getParameter("email", request)%>">
                   <!--  &nbsp; --> 
                    <label>College Name:</label> 
    	             <%=HTMLUtility.getList("collegename", String.valueOf(bean.getCollegeId()), clist)%>
               <!--      &nbsp;   
        	    -->      <input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH%>">
        	       
        	         <input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET%>">
        	         
                 </td>
                </tr>
            </table>
            
            <br>
            
            <table border="1" width="100%" align="center" cellpadding=6px cellspacing=".2">
                <tr style="background: skyblue">
                <th><input type="checkbox" id="select_all" name="select">Select All.</th>
                
                <th>S No.</th>
                <th>College.</th>
                <th>First Name.</th>
                <th>Last Name.</th>
                <th>Date Of Birth.</th>
                <th>Mobile No.</th>
                <th>Email_Id.</th>
                <th>Edit</th>
                </tr>
                
                <%
                	while(it.hasNext())
                	{
                	 bean = it.next();
                %>
                
                
                
       <tr align="center">
           	<td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId() %>">
                    <td><%=index++%></td>
                    <td><%=bean.getCollegeName()%></td>
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getLastName()%></td>
                    <td><%=bean.getDob()%></td>
                    <td><%=bean.getMobileNo()%></td>
                    <td><%=bean.getEmail()%></td>
                    <td><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                <%if(pageNo == 1){ %>
                    <td><input type="submit" name="operation" disabled="disabled" value="<%=StudentListCtl.OP_PREVIOUS%>">
       				<%}else{ %>
       				<td><input type="submit" name="operation"  value="<%=StudentListCtl.OP_PREVIOUS%>"></td>
       				<%} %>		
                     
                     <td><input type="submit" name="operation" value="<%=StudentListCtl.OP_DELETE%>"> </td>
                    <td> <input type="submit" name="operation" value="<%=StudentListCtl.OP_NEW%>"></td>
                    
                  <% StudentModel model = new StudentModel();                  
                  %>  
                  <% if(list.size() < pageSize || model.nextPK()-1 == bean.getId()){ %>
                  <td align="right"> <input type="submit" name="operation" disabled="disabled" value="<%=StudentListCtl.OP_NEXT%>"></td>
  					<%}else{ %>                   
  				  <td align="right"> <input type="submit" name="operation"  value="<%=StudentListCtl.OP_NEXT%>"></td>
   					<%} %>                 
                    
                </tr>
            </table>
            		<%}if(list.size() == 0){ %>
            		<td align="center"><input type="submit" name="operation" value="<%=StudentListCtl.OP_BACK%>"></td>	
            		<% } %>
            	
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
        </br>
         </br>
         </br>
                   </br>
                   </br>
                   </br>
    </center>

 <%@include file="Footer.jsp"%>
 </body>
</html>