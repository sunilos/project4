<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.controller.GetMarksheetCtl"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Get marksheet</title>



</head>
<body>
    <jsp:useBean id="bean" class="com.rays.pro4.Bean.MarksheetBean" scope="request"></jsp:useBean>
   <form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">
    <%@ include file="Header.jsp"%>


			<input type="hidden" name="id" value="<%=bean.getId()%>">

    <center>
        <div align="center">
	        <h1>Get Marksheet</h1>
	
        	<h3><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font></h3>
       		<H3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></H3> 
        </div>
        
            <table>
          	<tr><th align="left"> Roll No <span style="color:red">*</span> :</th>
				<td><input type="text" name="rollNo" placeholder="Enter RollNo." size="25" value="<%=ServletUtility.getParameter("rollNo", request)%>">
				</td><td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
				</tr>                
               
                
                <tr><th style="padding: 3px"></th></tr>
                
                <tr><th></th><td> &nbsp;&emsp;<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>">
                			&nbsp;&nbsp;<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_RESET%>">
                </td></tr>
                
                </table>

            <%--     <%
                    if (bean.getRollNo() != null  && bean.getRollNo().trim().length() > 0 ) {
                %> --%>
                
                    <%
                    if (bean.getName()!= null  && bean.getName().length() >= 0 ) {
                %>
                
			<table >
				<table border="1" width="100%">
				  <tr align="center" style="background: skyblue">
				  	<td><h2>Rays Technologies</h2></td>
				  </tr></table>
				  
				 <table border="1" width="100%">
				 	<tr align="center" style="background: darkcream">
				 		<th> Name</th>
				 		<td> <%=DataUtility.getStringData(bean.getName()) %></td>
				 		<th> Roll No</th>
				 		<td> <%=DataUtility.getStringData(bean.getRollNo()) %> </td>
				 						 
					</tr>
				 	<tr align="center" style="background: darkcream">
				 		<td> Status</td>
				 		<th>Regular</th>
				 		<td> Course</td>
				 		<th>BE</th>				 
					</tr>			
				 </table> 
	<%
	int phy =DataUtility.getInt(DataUtility.getStringData(bean.getPhysics()));
	int chem =DataUtility.getInt(DataUtility.getStringData(bean.getChemistry()));
	int math =DataUtility.getInt(DataUtility.getStringData(bean.getMaths()));
	int total = (phy+chem+math);
	float perc = total/3;
	%>
					  
			<table border="1" width="100%">
			
				<tr align="center" style="background: skyblue" style="width: 25%">
					<th>Subject</th>
					<th>Maximum Marks</th>
					<th>Minimum Marks</th>
					<th>Marks Obtained</th>
					<th>Grade</th>
				</tr>
				
				<tr align="center" style="background: darkcream">
				<td>Physics</td>
				<td>100</td>
				<td>33</td>
				<td><%=phy %>
				
				<%if(phy<33){%>
					<span style="color: red">*</span>
				<% } %>	</td>
				
				<td align="center" style="background: darkcream">
				
				<%
					if(phy <=100 && phy >85){ %> A+
				<%} else if(phy<=85 && phy > 75 ) {%>B+
				<%} else if(phy<=75 && phy > 65 ) {%>B
				<%} else if(phy<=65 && phy > 55 ){ %>C+
				<%} else if(phy<=55 && phy >=33  ){ %>C
				
				<%} else if(phy< 33 && phy >= 0 ) {%><span style="color: red"> Fail</span>
				<% } %>
				</td>
				</tr>
			
			<tr align="center" style="background: darkcream">
				<td>Chemistry</td>
				<td>100</td>
				<td>33</td>
				<td><%=chem %>
				
				<%if(chem<33){%>
					<span style="color: red">*</span>
				<% } %>	</td>
				
				<td align="center" style="background: darkcream">
				
				<%
					if(chem <=100 && chem >85){ %> A+
				<%} else if(chem<=85 && chem > 75 ) {%>B+
				<%} else if(chem <=75 && chem > 65 ) {%>B
				<%} else if(chem <=65 && chem > 55 ){ %>C+
				<%} else if(chem <=55 && chem >=33  ){ %>C
				
				<%} else if(chem < 33 && chem >= 0 ) {%><span style="color: red"> Fail</span>
				<% } %>
				</td>
				</tr>
					
			<tr align="center" style="background: darkcream">
				<td> Maths</td>
				<td>100</td>
				<td>33</td>
				<td><%=math %>
				
				<%if(math<33){%>
					<span style="color: red">*</span>
				<% } %>	</td>
				
				<td align="center" style="background: darkcream">
				
				<%
					if(math <=100 && math >85){ %> A+
				<%} else if(math <=85 && math > 75 ) {%>B+
				<%} else if( math <=75 && math > 65 ) {%>B
				<%} else if(math <=65 && math > 55 ){ %>C+
				<%} else if(math <=55 && math >=33  ){ %>C
				
				<%} else if(math < 33 && math >= 0 ) {%><span style="color: red"> Fail</span>
				<% } %>
				</td>
				</tr>
			</table>	  
			
			<table border="1" width="100%">
			<tr style="background: skyblue">
				<th>Total</th>
				<th>Percentage</th>
				<th>Division</th>
				<th>Result</th>
			</tr>
			<tr>
				<th style="background: darkcream"><%=total %>
				<% if(total<99 || phy<33|| chem<33|| 	math<33){ %>
				<span style="color: red">*</span>
				<% } %>
				</th>
			
				<th style="background: darkcream"><%=perc %> %</th>
				<th>
				<% if(perc <100 && perc >= 60){ %>1<sup>st</sup>
				<%} else if(perc <60 && perc >=40){ %>2<sup>nd</sup>
				<%} else if(perc <40 && perc >=0){ %>3<sup>rd</sup>
				<%} %>
				</th>
				
				<th style="background: darkcream">
				<%
				if(phy >=33 && chem>=33 && math >= 33) { %>
				<span style="color: green"> Pass</span>
				<%}else{ %>
				<span style="color: red"> Fail</span>
				<% } %>
				
				</th>
				</tr>
			</table>
			
			<%} %>
	</table>		
 </form>
    </center>
    </div>
    <%@ include file="Footer.jsp"%>
</body>
</html>