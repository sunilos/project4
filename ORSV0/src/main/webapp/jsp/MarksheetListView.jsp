<%@page import="com.sunilos.p4.ctl.MarksheetListCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
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
   
<h1>Marksheet List</h1>

<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="POST">

	<input type="hidden" name="pageNo" value="<%=pageNo%>"> 
	<input type="hidden" name="pageSize" value="<%=pageSize%>">

   <div class="table-container">
      <table class="border-separated-table" >
         <tr>
            <td align="center">
               <label> Name :</label> 
               <input type="text" name="name" value="<%=ServletUtility.getParameter("name", request)%>">&nbsp; 
               <label>RollNo :</label> 
               <input type="text" name="rollNo" value="<%=ServletUtility.getParameter("rollNo", request)%>">&nbsp;

               <input  class="primary-btn" type="submit" name="operation" value="<%=BaseCtl.OP_SEARCH %>">&nbsp;
               <a  class="success-btn" href="MarksheetCtl">Add</a>&nbsp;
               <input  class="danger-btn" type="submit" name="operation" value="<%=BaseCtl.OP_DELETE%>">
            </td>
         </tr>
      </table>
      <br>
      <table class="border-separated-table" >
         <tr>
            <th>Select</th>
            <th>SNo</th>
            <th>ID</th>
            <th>RollNo</th>
            <th>Name</th>
            <th>Physics</th>
            <th>Chemistry</th>
            <th>Maths</th>
            <th>Edit</th>
         </tr>
         <tr>
            <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
         </tr>
         <%
            while (it.hasNext()) {
            	MarksheetBean bean = it.next();
            %>
         <tr>
            <td><input type="checkbox" name="ids" value="<%=bean.getId()%>"></td>
            <td><%=index++%></td>
            <td><%=bean.getId()%></td>
            <td><%=bean.getRollNo()%></td>
            <td><%=bean.getName()%></td>
            <td><%=bean.getPhysics()%></td>
            <td><%=bean.getChemistry()%></td>
            <td><%=bean.getMaths()%></td>
            <td><a class="primary-btn" href="MarksheetCtl?id=<%=bean.getId()%>">Edit</a></td>
         </tr>
         <%
            }
            %>
      </table>

      <%@ include file="ListFooter.jsp"%>

   </div>
</form>