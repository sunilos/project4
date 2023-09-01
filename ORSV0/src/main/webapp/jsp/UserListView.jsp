<%@page import="com.sunilos.p4.ctl.UserListCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sunilos.p4.bean.UserBean"%>

<%
   int pageNo = ServletUtility.getPageNo(request);
   int pageSize = ServletUtility.getPageSize(request);
   int index = ((pageNo - 1) * pageSize) + 1;
   
   List list = ServletUtility.getList(request);
   Iterator<UserBean> it = list.iterator();
   %>

<h1>User List</h1>

<form action="<%=ORSView.USER_LIST_CTL%>" method="POST">

   <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
   <input type="hidden" name="pageSize" value="<%=pageSize%>">

   <div class="table-container">
      <table width="100%">
         <tr>
            <td align="center"><label>FirstName :</label> <input
               type="text" name="firstName"
               value="<%=ServletUtility.getParameter("firstName", request)%>">
               &emsp; <label>LoginId:</label> <input type="text" name="login"
                  value="<%=ServletUtility.getParameter("login", request)%>">
               <input  class="primary-btn" type="submit" name="operation" value="<%=BaseCtl.OP_SEARCH %>">&nbsp;
               <a  class="success-btn" href="UserCtl">Add</a>&nbsp;
               <input  class="danger-btn" type="submit" name="operation" value="<%=BaseCtl.OP_DELETE%>">	
            </td>
         </tr>
      </table>
      <br>
      <table class="border-separated-table">
         <tr>
            <th>Select</th>
            <th>SNo</th>
            <th>FirstName</th>
            <th>LastName</th>
            <th>LoginId</th>
            <th>Gender</th>
            <th>DOB</th>
            <th>Edit</th>
         </tr>
         <tr>
            <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
         </tr>
         <%
            while (it.hasNext()) {
            	UserBean bean = it.next();
            %>
         <tr>
            <td><input type="checkbox" name="ids" value="<%=bean.getId()%>"></td>
            <td><%=index++%></td>
            <td><%=bean.getFirstName()%></td>
            <td><%=bean.getLastName()%></td>
            <td><%=bean.getLogin()%></td>
            <td><%=bean.getGender()%></td>
            <td><%=bean.getDob()%></td>
            <td><a class="primary-btn" href="UserCtl?id=<%=bean.getId()%>">Edit</a></td>
         </tr>
         <%
            }
            %>
      </table>
      <%@ include file="ListFooter.jsp"%>
   </div>
</form>