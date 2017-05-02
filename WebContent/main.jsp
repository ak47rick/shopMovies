<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1" import="FabFlix.*, java.util.*, java.text.*"%>
<%@ include file = "menu.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Main</title>
</head>
<body>

<H1 ALIGN="CENTER">Welcome to FabFlix</H1>

<div id="form_div">
	<form action="Search.jsp" method="get">
	<input type="submit" name="Go to Search Menu" value="Go to Search Menu">				
	</form>
	
	<br>
	<form action = "Browse.jsp" method = "get">
	<input type ="submit" name = "Go to Browse Menu" value = "browse">
	</form>		

</body>
</html>



