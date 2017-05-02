package FabFlix;


import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHelper extends HttpServlet{
	private static final long serialVersionUID = 1L;	
	
	public LoginHelper(){
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String u_id = "";
		//handles logout. Invalidates the session if request has parameter for "logout"(which is sent only when user clicks logout on the menu)
		if(request.getParameter("logout")!= null){			
			HttpSession s = request.getSession(false);
			s.invalidate();
			response.sendRedirect("Index.jsp");		
			return;
		}
					
		String email1 = (String) request.getParameter("email");
		String pw1 = (String) request.getParameter("password");	
		
		if(email1.equals("") && pw1.equals("")){
			HttpSession s = request.getSession(false);
			s.invalidate();
			response.sendRedirect("Index.jsp");	
			return;			
		}
		boolean logged = false;
			
		String query = "SELECT c_id FROM customers WHERE email=\'" + email1 + "\' AND password=\'" + pw1
				+ "\';";		
		
		
		/*if(request.getSession().getAttribute("Flag")!= null){
			request.getSession().invalidate();
		}*/
		
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false","mytestuser","mypassword");			
		request.getSession().setAttribute("connection",connection);
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs;
		
			rs = statement.executeQuery();
			if(rs.next()){				
				logged = true;				
				u_id = rs.getString(1);
				
			}				
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		
			if(logged){
				
				request.getSession().setAttribute("Flag", "true");
				//should be redirected to main page when its made.(right now it returns to login screen just for debugging purposes)
				
				//create a new cart when logged in.
				request.getSession().setAttribute("cart", new Cart());			
				request.getSession().setAttribute("cid",u_id);
				response.sendRedirect("main.jsp");				
			}
			else{
				request.getSession().setAttribute("Flag","error");
				response.sendRedirect("Index.jsp");				
			}
			
		
		
			
		
		
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		doPost(request,response);
		
	}
	
	
	
	
	
	
}
