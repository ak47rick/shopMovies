<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1" import="FabFlix.*, java.util.*, java.text.*"%>
<%@ include file="menu.jsp"%>

<html>
<head>
<title>Checkout</title>
</head>
<body>

<h2> Checkout </h2>

<%String msg = request.getParameter("msg");
if(msg != null)
{
	
	out.print("<h5>Credit card not found. Try again</h5>");
	
}
%>

<div id="form_div">
	<form action="checkOutHelper" method="post">
		<table style="font-size: small">
			<tr>
				<td width="30">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="30">&nbsp;</td>
				<td width="300">First Name: <input type="text"name="first"/></td>
			</tr>
			<tr>
				<td width="30">&nbsp;</td>
				<td width="300">Last Name: <input type="text"name="last"/></td>
			</tr>
			<tr>
				<td width="30">&nbsp;</td>
				<td width="300">Credit Card No.: <input type="text"name="cc_no"/></td>
			</tr>
			<tr>
				<td width="30">&nbsp;</td>
				<td width="300">Exp Date(YYYY-MM-DD): <input type="text"name="exp"/></td>
			</tr>
		</table>
		
	 <input type="submit" name="submit" value="submit">
</form>
</div>


</body>
</html>