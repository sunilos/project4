<%@page import="com.sunilos.p4.ctl.StudentListCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%
   int pageNo = ServletUtility.getPageNo(request);
   int pageSize = ServletUtility.getPageSize(request);
   int index = ((pageNo - 1) * pageSize) + 1;
   
   List list = ServletUtility.getList(request);
   Iterator<StudentBean> it = list.iterator();
   %>
<h1>Student List</h1>

<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="POST">

   <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
   <input type="hidden" name="pageSize" value="<%=pageSize%>">

   <div class="table-container">
      <table class="border-separated-table" >
         <tr>
            <td align="center">
               <label> FirstName :</label> 
               <input type="text" name="firstName" value="<%=ServletUtility.getParameter("firstName", request)%>">
               <label>LastName :</label> 
               <input type="text" name="lastName" value="<%=ServletUtility.getParameter("lastName", request)%>">
               <label>Email_Id:</label> 
               <input type="text" name="email" value="<%=ServletUtility.getParameter("email", request)%>">&nbsp;
               <input  class="primary-btn" type="submit" name="operation" value="<%=BaseCtl.OP_SEARCH %>">&nbsp;
               <a  class="success-btn" href="StudentCtl">Add</a>&nbsp;
               <input  class="danger-btn" type="submit" name="operation" value="<%=BaseCtl.OP_DELETE%>">                  
            </td>
         </tr>
      </table>
      <br>
      <table class="border-separated-table">
         <tr>
            <th>Select</th>
            <th>SNo</th>
            <th>ID.</th>
            <th>College.</th>
            <th>First Name.</th>
            <th>Last Name.</th>
            <th>Date Of Birth.</th>
            <th>Mobil No.</th>
            <th>Email ID.</th>
            <th>Edit</th>
         </tr>
         <tr>
            <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
         </tr>
         <%
            while (it.hasNext()) {
            	StudentBean bean = it.next();
            %>
         <tr>
            <td><input type="checkbox" name="ids" value="<%=bean.getId()%>"></td>
            <td><%=index++%></td>
            <td><%=bean.getId()%></td>
            <td><%=bean.getCollegeId()%></td>
            <td><%=bean.getFirstName()%></td>
            <td><%=bean.getLastName()%></td>
            <td><%=bean.getDob()%></td>
            <td><%=bean.getMobileNo()%></td>
            <td><%=bean.getEmail()%></td>
            <td><a class="primary-btn" href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
         </tr>
         <%
            }
            %>
      </table>
      <%@ include file="ListFooter.jsp"%>
   </div>
</form>