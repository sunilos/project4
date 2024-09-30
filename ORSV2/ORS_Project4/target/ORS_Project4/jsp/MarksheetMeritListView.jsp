<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rays.pro4.Bean.MarksheetBean"%>
<%@page import="com.rays.pro4.controller.MarksheetMeritListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> Marksheet Merit List</title>


</head>
<body>
<jsp:useBean id="bean" class="com.rays.pro4.Bean.MarksheetBean" scope="request" ></jsp:useBean>
        <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
    <%@include file="Header.jsp"%>
    
    <center>    
    
        <h1>Marksheet Merit List</h1>

            <br>
            <table border="1" width="100%" align="center" cellpadding=4px cellspacing=".2">
                <tr style="background: skyblue">

                    <th>S.No.</th>
                    <th>Roll No</th>
                    <th>Name</th>
                    <th>Physics</th>
                    <th>Chemistry</th>
                    <th>Maths</th>
					<th>Total</th>
					<th>Percentage</th>
                </tr>
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<MarksheetBean> it = list.iterator();

                    while (it.hasNext()) {

                        bean = it.next();
                
                        int phy = bean.getPhysics(); 
                		int chem = bean.getChemistry(); 
                		int maths =bean.getMaths();
                		int total = (phy+chem+maths);
                		float perc = total/3;
                		
                %>
                <tr align="center">

                    <td><%=index++%></td>
                    <td><%=bean.getRollNo()%></td>
                    <td><%=bean.getName()%></td>
                    <td><%=phy%></td>
                    <td><%=chem%></td>
                    <td><%=maths%></td>
					<td><%=total %></td>
					<td style="color:green" ><%=((perc) +"%") %></td>
                </tr>

                <%
                    }
                %>
            </table><br>
            <table>
                <tr>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
            
        </form>
    </center>
    </br>
    </br>
    </br>
                   </br>
                   </br>
                   </br>
    

    <%@include file="Footer.jsp"%>
</body>
</html>