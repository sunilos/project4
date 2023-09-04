<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
</head>

<%
      StringBuffer errorBuffer = new StringBuffer();
            
      // Create a ByteArrayOutputStream to capture the output
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            
      // Create a custom PrintStream that writes to the ByteArrayOutputStream
      PrintStream printStream = new PrintStream(byteArrayOutputStream);
      
      // Redirect System.err to the custom PrintStream
      System.setErr(printStream);
            
      // Print the error message and stack trace to System.err
      exception.printStackTrace();
      
      // Capture the error message and stack trace from the ByteArrayOutputStream
      String errorMessageAndStackTrace = byteArrayOutputStream.toString();

      // Close the custom PrintStream and reset System.err
      printStream.close();
      System.setErr(System.err); // Reset System.err to its original state

%>

<body>
    <h1>Error Occurred</h1>
    <p>An error occurred in the application. Please try again later.</p>
    <hr>
	<pre>
		<%=errorMessageAndStackTrace%>
	</pre>
</body>
</html>