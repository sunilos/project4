<%@page import="com.rays.pro4.controller.PaymentCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <jsp:useBean id="bean" class="com.rays.pro4.Bean.PaymentBean" scope="request"></jsp:useBean>
   <%@ include file="Header.jsp"%>
   
   <center>

    <form action="<%=ORSView.PAYMENT_CTL%>" method="post">

        <%
            List l = (List) request.getAttribute("roleList");
        %>

        
    <div align="center">    
            <h1>
 				
           		<% if(bean != null && bean.getId() > 0) { %>
            <tr><th><font size="5px"> Update Payment </font>  </th></tr>
            	<%}else{%>
			<tr><th><font size="5px"> Add Payment </font>  </th></tr>            
            	<%}%>
            </h1>
   
            <h3><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
            <font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
	       
</div>
            <input type="hidden" name="cid" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

            <table>
                <tr>
                    <th align="left">Payment Type<span style="color: red">*</span> :</th>
                    <td><input type="text" name="cName" placeholder="Enter Payment" size="25"  value="<%=DataUtility.getStringData(bean.getC_Name())%>"></td>
                    <td style="position: fixed "><font color="red"><%=ServletUtility.getErrorMessage("cName", request)%></font></td> 
                    
                </tr>
    
    <tr><th style="padding: 3px"></th></tr>          
              
              <tr>
                    <th align="left">Payment Mode<span style="color: red">*</span> :</th>
                    <td><input type="text" name="accu" placeholder="Enter Payment Mode" size="25" value="<%=DataUtility.getStringData(bean.getAccount())%>"></td>
                     <td style="position: fixed"><font  color="red"> <%=ServletUtility.getErrorMessage("accu", request)%></font></td>
                </tr>
   
    <tr><th style="padding: 3px"></th></tr>          

              
                <tr ><th></th>
                <%
                if(bean.getId()>0){
                %>
                <td colspan="2" >
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=PaymentCtl.OP_UPDATE%>">
                      &nbsp;  &nbsp;
                  
                
                <% }else{%>
                
                <td colspan="2" > 
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=PaymentCtl.OP_SAVE%>">
                
                <% } %>
                
                </tr>
            </table>
    </form>
    </center>

    <%@ include file="Footer.jsp"%>
	
</body>
</html>
