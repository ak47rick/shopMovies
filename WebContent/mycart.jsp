<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1" import="FabFlix.*, java.util.*, java.text.*, java.sql.*"%>
<%@ include file = "menu.jsp" %>

<%Connection connection = (Connection) request.getSession().getAttribute("connection");
if(connection == null){
request.getSession().invalidate();
response.sendRedirect("Index.jsp");
return;}


Statement st = connection.createStatement();
String query = "SELECT title FROM movies WHERE m_id = ";
Cart my_cart = (Cart) request.getSession().getAttribute("cart");

String msg = (String) request.getSession().getAttribute("cid");
%>

<h2> Welcome Customer <% out.print(msg); %></h2>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My Cart</title>
</head>
<style>
table, th, td {
    border: 1px solid black;  
}
</style>
<body>

<br><br>

<div class="form">
	
	<table border align ="center">

		<tr>
		<th>Title</th>		
		<th>Quantity</th>
		<th>Modify</th>
		<th>Remove</th>
		</tr>
<%
HashMap<Integer,Integer> to_print = my_cart.get_cart();
out.print("<tr>");
for(Map.Entry<Integer,Integer> entry : to_print.entrySet()){	
	String query2 = query + entry.getKey() + ";";
	ResultSet rs = st.executeQuery(query2);
	
	if(rs.next()){
	out.print("<td>" + rs.getString(1) +"</td>" + "<td>" + entry.getValue() + "</td>");			
	%>	
	
	
	<td>
	
	<form action="modifyCart" method = "GET">
	<input type="text" name ="val" size ="1" maxlength="3" required/>
	<input type="hidden" name="m_id" value = <% out.print(entry.getKey()); %>>	
	<input type="submit" name ="submit" value = "modify"/>
	</form>		
	</td>
	
	
	<%	out.println("<td><a href=\"removeCart?m_id=" + entry.getKey() +"\"> Remove </a>");   %>
	
	</td>			
	
	<%	}%></tr> <%} %>

</table>
</div>



<br><br>  <a href = "checkout.jsp" style ="margin:auto; text-align:center; display:block; font-size:2em;" class="button large hpbottom">Checkout</a>

</body>
</html>