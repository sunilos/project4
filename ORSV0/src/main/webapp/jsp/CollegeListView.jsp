<%@page import="com.sunilos.p4.ctl.CollegeListCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="com.sunilos.p4.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%
   int pageNo = ServletUtility.getPageNo(request);
   int pageSize = ServletUtility.getPageSize(request);
   int index = ((pageNo - 1) * pageSize) + 1;
   
   List list = ServletUtility.getList(request);
   Iterator<CollegeBean> it = list.iterator();
   %>
   
<h1>College List</h1>

<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST" >

   <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
   <input type="hidden" name="pageSize" value="<%=pageSize%>">

   <div class="table-container">
      <table class="border-separated-table">
         <tr>
            <td align="center">
               <label> Name :</label> 
               <input type="text" name="name" value="<%=ServletUtility.getParameter("name", request)%>">
               <label>City :</label> 
               <input type="text" name="city" value="<%=ServletUtility.getParameter("city", request)%>">
               <input  class="primary-btn" type="submit" name="operation" value="<%=BaseCtl.OP_SEARCH %>">&nbsp;
               <a  class="success-btn" href="CollegeCtl">Add</a>&nbsp;
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
            <th>Name.</th>
            <th>Address.</th>
            <th>State.</th>
            <th>City.</th>
            <th>PhoneNo.</th>
            <th>Edit</th>
         </tr>
         <tr>
            <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
         </tr>
         <%
            while (it.hasNext()) {
            	CollegeBean bean = it.next();
            %>
         <tr>
            <td><input type="checkbox" name="ids" value="<%=bean.getId()%>"></td>
            <td><%=index++%></td>
            <td><%=bean.getId()%></td>
            <td><%=bean.getName()%></td>
            <td><%=bean.getAddress()%></td>
            <td><%=bean.getState()%></td>
            <td><%=bean.getCity()%></td>
            <td><%=bean.getPhoneNo()%></td>
            <td><a class="primary-btn" href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
         </tr>
         <%
            }
            %>
      </table>
      <%@ include file="ListFooter.jsp"%>
   </div>
</form>