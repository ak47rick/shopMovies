<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" import="java.io.IOException, FabFlix.*, java.util.*, java.text.*,
    java.sql.*"%>
<%@ include file = "menu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Browsing</title>
</head>
<body>

<h1 align ='center'>This is browsing page</h1>
<h3 align ='left'> Genres</h3>
<%
try{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false","mytestuser","mypassword");			
	Statement select = connection.createStatement();
	ResultSet result = select.executeQuery("Select genres.name from genres");
	while (result.next()){
%>
		<a href= "browsing?genre=<%=result.getString(1)%>"> <%=result.getString(1)%> </a> &nbsp&nbsp
<%
	}
	char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
%>
	</br>
	</br>
	<h3 align ='left'> Browsing by Alphabet</h3>
<%
	for (char c: alphabet){
		%>
		<a href= "browsing?letter=<%=c%>"> <%=c%> </a> &nbsp&nbsp
<% 
	}
} catch (Exception e) {
	e.printStackTrace();
}
%>
</body>
</html>