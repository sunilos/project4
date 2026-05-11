<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataValidator"%>
<%@page import="com.sunilos.p4.util.MessageSource"%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.sunilos.com/ors-tags" prefix="ors" %>

<html>
   
   <%
    String contextPath = request.getContextPath();
   %>
   
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>ORS &mdash; Online Result System</title>
      <link rel="icon" href="<%= contextPath %>/img/favicon.ico" type="image/x-icon">
      <!-- Bootstrap 5 -->
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
      <!-- Bootstrap Icons -->
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
      <!-- Font Awesome -->
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">
      <!-- Custom overrides -->
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
      
      <div class="page-content">
         <jsp:include page="<%= viewName %>" />
      </div>
      
      <%@include file="jsp/Footer.jsp"%>
      <!-- Bootstrap 5 JS -->
      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
   </body>
</html>