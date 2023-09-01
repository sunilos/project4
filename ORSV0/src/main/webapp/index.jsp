<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataValidator"%>
<html>
   <head>
      <link rel="icon" href="../img/favicon.ico" type="image/x-icon">
      <link rel="stylesheet" href="../css/app.css">
      <script type="text/javascript" src="../js/calendar.js"></script>
   </head>
   <body>
      <%@ include file="jsp/Header.jsp"%>
      <%
         String viewName = request.getParameter("p");
         if(DataValidator.isNull(viewName)){
          viewName = (String)request.getAttribute("p");
         }
         if(DataValidator.isNull(viewName)){
          viewName = "jsp/404.jsp";
         }
         //String viewPath = "jsp/" + viewName;
         %>
      <center>
         <jsp:include page="<%= viewName %>" />
      </center>
      <%@include file="jsp/Footer.jsp"%>
   </body>
</html>