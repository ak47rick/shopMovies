<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1" import="FabFlix.*, java.util.*, java.text.*"%>
<%@ include file="menu.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome to FabFlix</title>
</head>
<body>

<h3>Login</h3>
 
 <%  
 HttpSession ss = request.getSession(true);
 String f = (String) ss.getAttribute("Flag");
 if(f != null){
 if("error".equals(f)){	
	 out.print("Login Failed. Try again!");	 
 }
 if("true".equals(f)){	 
	 response.sendRedirect("main.jsp");	
	 return;
 } 
 }
 %>
    
<div id="login form">
				<form action="LoginHelper" method="post">
					<table style="font-size: large">
						<tr>
							<td width="50">&nbsp;</td>							
						</tr>
						<tr>
							<td width="50">&nbsp;</td>
							<td width="200">email address: <input type="text"
								name="email" /></td>
						</tr>						
						<tr>
							<td width="50">&nbsp;</td>
							<td width="200"><div align="left">
									Password: <input type="password" name="password" />
								</div></td>
							<td></td>
						</tr>						
					</table>					
					<input type="submit" name="Login" value="submit">
				</form>
			</div>

</body>
</html>