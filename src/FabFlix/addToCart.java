package FabFlix;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class addToCart extends HttpServlet{

	
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		
		if(request.getParameter("m_id") == null){
		response.sendRedirect("main.jsp");			
		}		
		Cart my_cart;
		HttpSession session = request.getSession();
		
		if(session.getAttribute("cart") == null){
			
			my_cart = new Cart();
		}
		else{
			my_cart = (Cart) session.getAttribute("cart");
		}
		
		Integer m_id = Integer.parseInt(request.getParameter("m_id"));		
		
		my_cart.add_to_cart(m_id);				
			
		response.sendRedirect("mycart.jsp?msg=addsuccessful");	
	
	}
	
	
	
	
	
	
	
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		doGet(request,response);
		
	}
	
	
	
	
	
	
	
	
}
