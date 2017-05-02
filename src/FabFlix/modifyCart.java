package FabFlix;

import javax.servlet.http.HttpServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class modifyCart extends HttpServlet{

	private static final long serialVersionUID = 1L;





	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		if(request.getParameter("m_id") == null || request.getParameter("val") == null || "".equals(request.getParameter("val"))){		
		response.sendRedirect("mycart.jsp");	
		return;			
		}
		
		String m_id = (String) request.getParameter("m_id");
		Integer val = Integer.parseInt(request.getParameter("val"));
				
		
		if(val <= 0){			
			String ret = "removeCart?m_id="+ m_id;
			response.sendRedirect(ret);			
		}
		
		else{
			Cart my_cart;
			HttpSession session = request.getSession();
			
			if(session.getAttribute("cart") == null){
				
				my_cart = new Cart();
			}
			else{
				my_cart = (Cart) session.getAttribute("cart");
			}
			
			my_cart.modify_cart(Integer.parseInt(m_id), val);
			response.sendRedirect("mycart.jsp");
		}
		
		
		
		
		
	}
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		doGet(request,response);
		
	}
	
	
	
	
	
	
	
	
	
}
