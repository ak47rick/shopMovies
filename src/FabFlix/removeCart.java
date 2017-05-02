package FabFlix;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class removeCart extends HttpServlet{

	
	
	
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
	
			if(request.getParameter("m_id") == null){			
			response.sendRedirect("mycart.jsp");	
			return;			
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
			
			
			my_cart.remove_from_cart(m_id);
			response.sendRedirect("mycart.jsp");
		
			
			/*else if(m.equals("modify")){				
				my_cart.modify_cart(m_id,Integer.parseInt(v));
				response.sendRedirect("mycart.jsp");
			}
			*/
						
		
	
	
	}
	
	
	
		
		
		
		
		
		
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			
			doGet(request,response);
			
		}
	
	
	
}
