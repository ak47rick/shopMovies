<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="EUC-KR"%>
<%@ include file = "menu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Movie</title>
</head>
<body>

<h3>Search for a movie</h3>
 
 <%  if(request.getSession().getAttribute("sFlag") != null){
	 if("true".equals((String)request.getSession().getAttribute("sFlag"))){
	 out.print("At least one field is required for a search"); 
	 request.getSession().setAttribute("sFlag", "false");	 
	 }			 
 } 
 %> 
 <br>
 
 
<div id="form_div">
				<form action="SearchMovie" method="get">
					<table style="font-size: small">
						<tr>
							<td width="35">&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td width="300">Title: <input type="text"
								name="title" /></td>
						</tr>
						<tr>
							<td width="35"><div class="tinyspacer">&nbsp;</div></td>
							<td width="300"><div class="tinyspacer">&nbsp;</div></td>
							<td><div class="tinyspacer">&nbsp;</div></td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td width="300"><div align="left">
									Year: <input type="text" name="year" />
								</div></td>
							<td></td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td width="300"><div align="left">
									Director: <input type="text" name="director" />
								</div></td>
							<td></td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td width="100"><div align="left">
									Star's First Name: <input type="text" name="first" />
								</div></td>
							<td></td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td width="100"><div align="left">
									Star's Last Name: <input type="text" name="last" />
								</div></td>
							<td></td>
						</tr>
					</table>
					<input type="submit" name="Login" value="submit">
				</form>
			</div>

</body>
</html>