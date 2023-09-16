<!--
*Uses example of Tag lib
*example of i18n
-->


<%@ taglib uri="http://www.sunilos.com/ors-tags" prefix="ors" %>

<%@page import="com.sunilos.p4.ctl.MarksheetCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="java.util.List"%>
<%@page import="com.sunilos.p4.util.HTMLUtility"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.MarksheetBean"
   scope="request"></jsp:useBean>

<h1>Marksheet</h1>

<form action="<%=ORSView.MARKSHEET_CTL%>" method="POST">

  <input type="hidden" name="id" value="<%=bean.getId()%>">
  <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
  <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
  <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
  <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

   
  	  <ors:formMsg />   

      <table>
         <tr>
            <th><ors:message key="marksheet.rollno"/>*</th>
            <td><input type="text" name="rollNo"
               value="<%=DataUtility.getStringData(bean.getRollNo())%>"
               <%=(bean.getId() > 0) ? "readonly" : ""%>> <font
               color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
         </tr>
         <tr>
            <th><ors:message key="marksheet.name"/>*</th>
            <td>
               <ors:formSelect name="studentId" value="<%=String.valueOf(bean.getStudentId())%>" list="studentList" />
            </td>
         </tr>
         <tr>
            <th><ors:message key="marksheet.physics"/></th>
            <td><input type="text" name="physics"
               value="<%=DataUtility.getStringData(bean.getPhysics())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td>
         </tr>
         <tr>
            <th><ors:message key="marksheet.chemistry"/></th>
            <td><input type="text" name="chemistry"
               value="<%=DataUtility.getStringData(bean.getChemistry())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>
         </tr>
         <tr>
            <th><ors:message key="marksheet.maths"/></th>
            <td><input type="text" name="maths"
               value="<%=DataUtility.getStringData(bean.getMaths())%>"><font
               color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td>
         </tr>
         <tr>
            <th></th>
            <td colspan="2">
            
               <button class="primary-btn" name="operation" type="submit" value="<%=MarksheetCtl.OP_SAVE%>"><ors:message key="button.save"/></button>	
               <%if (bean.getId() > 0) { %>
               	<button class="danger-btn" name="operation" type="submit" value="<%=MarksheetCtl.OP_DELETE%>"><ors:message key="button.delete"/></button>	
               <%} %>   
               <a class="secondary-btn" href="MarksheetListCtl?id=0"><ors:message key="button.cancel"/></a> 
            </td>
         </tr>
      </table>
</form>