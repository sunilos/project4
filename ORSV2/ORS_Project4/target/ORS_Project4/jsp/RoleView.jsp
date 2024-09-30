<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.RoleCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Role Register</title>



</head>
<body>
        <jsp:useBean id="bean" class="com.rays.pro4.Bean.RoleBean" scope="request"></jsp:useBean>
    <form action="<%=ORSView.ROLE_CTL%>" method="post">
        <%@ include file="Header.jsp"%>
        
        
        <center>
        
           
<div align="center">
			
			<% if( bean != null && bean.getId() >0 ){ %>
			 <h1>Update Role</h1>
			<%}else{%>
			 <h1>Add Role</h1>
			<% }%>
	
            <H3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></H2>
            <H2><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font></H2>
</div>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

            <table>
                <tr>
                    <th align="left">Name <span style="color:red">*</span> :</th>
                    <td><input type="text" name="name" placeholder="Enter Role Name" Size="25"
                          value="<%=DataUtility.getStringData(bean.getName())%>">
                         </td><td style="position: fixed"> <font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
                </tr>
             <tr><th style="padding: 3px"></th></tr>     
                <tr>
                    <th align="left">Description <span style="color:red">*</span> :</th>
                    <td><input type="text" name="description"  placeholder="Enter Description" Size="25"
                        value="<%=DataUtility.getStringData(bean.getDescription())%>">
                        </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
                </tr>
                  <tr><th style="padding: 3px"></th></tr>     
                <tr><th></th>                
           
                <%if(bean.getId()>0) {%>    
                <td colspan="2">
                     &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=RoleCtl.OP_UPDATE%>"> 
                   
                    &nbsp;  &nbsp; <input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL%>"></td>
                <%}else{ %>
                	<td colspan="2">
                     &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=RoleCtl.OP_SAVE%>"> 
                     &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=RoleCtl.OP_RESET%>"></td>
                <%} %>
                </tr>
            </table>
    </form>
    </center>
   
    <%@ include file="Footer.jsp"%>
</body>
</html>
