<%@page import="com.sunilos.p4.ctl.CollegeCtl"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.CollegeBean" scope="request"></jsp:useBean>

<h1>Add College</h1>

<form action="CollegeCtl" method="POST">

   <input type="hidden" name="id" value="<%=bean.getId()%>">
   <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
   <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
   <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
   <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

   <center>
      <p class="error-message">
         <%=ServletUtility.getErrorMessage(request)%>
      </p>
      <p class="success-message">
         <%=ServletUtility.getSuccessMessage(request)%>
      </p>
      <table>
         <tr>
            <th>Name*</th>
            <td><input type="text" name="name"
               value="<%=DataUtility.getStringData(bean.getName())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
         </tr>
         <tr>
            <th>Address*</th>
            <td><input type="text" name="address"
               value="<%=DataUtility.getStringData(bean.getAddress())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
         </tr>
         <tr>
            <th>State*</th>
            <td><input type="text" name="state"
               value="<%=DataUtility.getStringData(bean.getState())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
         </tr>
         <tr>
            <th>City*</th>
            <td><input type="text" name="city"
               value="<%=DataUtility.getStringData(bean.getCity())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
         </tr>
         <tr>
            <th>PhoneNo*</th>
            <td><input type="text" name="phoneNo"
               value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
         </tr>
         <tr>
            <th></th>
            <td colspan="2"><input class="primary-btn" type="submit" name="operation"
               value="<%=BaseCtl.OP_SAVE%>">
               <%
                  if (bean.getId() > 0) {
                  %> &emsp;<input class="danger-btn" type="submit" name="operation"
                  value="<%=BaseCtl.OP_DELETE%>"> <%
                  }
                  %>&emsp; <a class="secondary-btn" href="CollegeListCtl?id=0" >Cancel</a>
            </td>
         </tr>
      </table>
   </center>
</form>