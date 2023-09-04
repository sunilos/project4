<%@page import="com.sunilos.p4.ctl.GetMarksheetCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<jsp:useBean id="bean" class="com.sunilos.p4.bean.MarksheetBean" scope="request"></jsp:useBean>
<h1>Get Marksheet</h1>
<center>
   <p class="success-message">
      <%=ServletUtility.getSuccessMessage(request)%>
   </p>
   <p class="error-message">
      <%=ServletUtility.getErrorMessage(request)%>
   </p>
   <div>
      <form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="POST">
         <label>RollNo :</label>&nbsp;
         <input type="text" name="rollNo" value="<%=ServletUtility.getParameter("rollNo", request)%>">
         &nbsp;
         <font color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font>
         <input type="submit" class="primary-btn" name="operation" value="<%=GetMarksheetCtl.OP_GO%>">
      </form>
   </div>
   <%
      if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {
      %>
   <table>
      <tr>
         <td>Rollno :</td>
         <td><%=DataUtility.getStringData(bean.getRollNo())%></td>
      </tr>
      <tr>
         <td>Name :</td>
         <td><%=DataUtility.getStringData(bean.getName())%></td>
      </tr>
      <tr>
         <td>Physics :</td>
         <td><%=DataUtility.getStringData(bean.getPhysics())%></td>
      </tr>
      <tr>
         <td>Chemistry :</td>
         <td><%=DataUtility.getStringData(bean.getChemistry())%></td>
      </tr>
      <tr>
         <td>Maths :</td>
         <td><%=DataUtility.getStringData(bean.getMaths())%></td>
      </tr>
      <tr>
         </td>
      </tr>
   </table>
   <%
      }
      %>
</center>