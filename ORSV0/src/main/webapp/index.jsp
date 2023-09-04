<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataValidator"%>
<html>
   
   <%
    String contextPath = request.getContextPath();
   %>
   <head>
      <link rel="icon" href="<%= contextPath %>/img/favicon.ico" type="image/x-icon">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">
      <link rel="stylesheet" href="<%= contextPath %>/css/app.css">
      <script type="text/javascript" src="<%= contextPath %>/js/calendar.js"></script>
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