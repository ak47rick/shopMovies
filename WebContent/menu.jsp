<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="FabFlix.*, java.util.*, java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='stylesheet.css'>
<title></title>
</head>
<body>

<% HttpSession s = request.getSession();
 String flag = (String) s.getAttribute("Flag");
 if ("true".equals(flag)){
	 %>
<div class="menu" ><a href ="main.jsp">Home</a>  <a href ="mycart.jsp">Cart</a>  <a href ="Search.jsp">Search</a> <a href ="Browse.jsp">Browse</a> <a href = "checkout.jsp">Checkout</a> <a href ="LoginHelper?logout=true">Logout</a>	</div> 	
	 <%
 }
 else {
	 %>
<div class="menu" ><a href ="Index.jsp">Home</a>  <a href ="Index.jsp">Login</a></div>  	 

<% 
 }
%>


</body>
</html>