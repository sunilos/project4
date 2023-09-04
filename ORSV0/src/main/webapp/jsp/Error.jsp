<%@page import="com.sunilos.p4.util.DataUtility"%>
<!DOCTYPE html>
<html>
   <head>
      <title>Error Page</title>
   </head>
   <body>
      <h1>Error Occurred</h1>
      <p>An error occurred in the application. Please try again later.</p>
      <hr>
      <pre>
		<%=DataUtility.exceptionToString(exception)%>
	</pre>
      <button onclick="history.back()">Go Back</button>
   </body>
</html>