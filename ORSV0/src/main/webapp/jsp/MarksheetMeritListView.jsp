<%@page import="com.sunilos.p4.ctl.MarksheetMeritListCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%
   int pageNo = ServletUtility.getPageNo(request);
   int pageSize = ServletUtility.getPageSize(request);
   int index = ((pageNo - 1) * pageSize) + 1;
   
   List list = ServletUtility.getList(request);
   Iterator<MarksheetBean> it = list.iterator();
   %>
   
<h1>Marksheet Merit List</h1>

<center>
   <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
      <div class="table-container">
      <table class="border-separated-table">
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
            <td align="right">
               <input type="submit" name="operation" class="primary-btn" value="<%=MarksheetMeritListCtl.OP_BACK%>">
            </td>
         </tr>
      </table>
      </div>
   </form>
</center>