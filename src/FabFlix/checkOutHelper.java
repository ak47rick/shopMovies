package FabFlix;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class checkOutHelper extends HttpServlet{
	private static final long serialVersionUID = 1L;	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
			
		
		if(request.getParameter("first") == null || request.getParameter("last") == null || request.getParameter("cc_no") == null || request.getParameter("exp") == null){
			response.sendRedirect("checkout.jsp");		
			return;
		}
		
		if(request.getSession().getAttribute("cart") == null || request.getSession().getAttribute("cid") == null){
			response.sendRedirect("checkout.jsp");		
			return;			
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date today = Calendar.getInstance().getTime(); 
		
		String date = sdf.format(today);
		
		System.out.println(date);
		
		String cid = (String) request.getSession().getAttribute("cid");
		String first = (String) request.getParameter("first");
		String last = (String) request.getParameter("last");
		String cc_no = (String) request.getParameter("cc_no");
		String exp = (String) request.getParameter("exp");
		Cart my_cart = (Cart) request.getSession().getAttribute("cart");
		boolean found = false;
		
		if(my_cart.isEmpty()){
			System.out.println("cart was empty. going back go checkout.jsp");
			response.sendRedirect("checkout.jsp");
			return;			
		}
		
		
		String query = "SELECT * FROM creditcards WHERE first_name =\"" + first + "\" AND last_name = \"" + last + "\" AND cc_id = \"" + cc_no + "\" AND expiration = \"" + exp + "\";"; 
		String query2 = "INSERT INTO sales (customer_id,movie_id,sale_date) VALUES (? , ?,  ? );";
		try{			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false","mytestuser","mypassword");
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()){
				found = true;
				System.out.println("found = true");
			}						
			
			if(found){
								
				HashMap<Integer,Integer> basket = my_cart.get_cart();
				
				for(Map.Entry<Integer,Integer> entry : basket.entrySet()){
					
					PreparedStatement st2 = connection.prepareStatement(query2);					
					st2.setString(1,cid);
					st2.setString(2,entry.getKey().toString());
					st2.setString(3,date);					
					int result = st2.executeUpdate();
					System.out.println(result+ "is the result of update query");
					
				}
				
				my_cart.emptyCart();	
				response.sendRedirect("confirmation.jsp");
				return;
			}
			
			
		}catch(SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e){
			
			System.out.print(e.getMessage());
			response.sendRedirect("checkout.jsp?msg=false");
			return;
	}	
	
	if(!found){		
		response.sendRedirect("checkout.jsp?msg=false");
		return;	
	}
		
		
				
		
			
	}
	
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		doPost(request,response);		
	}
	
	
	
}
