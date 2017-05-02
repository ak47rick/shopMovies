package FabFlix;

import java.util.HashMap;


public class Cart {	
	
	//(key -> movie id, value = quantity)
	private HashMap<Integer, Integer> cart = new HashMap<Integer, Integer>();	
	
		
	public void add_to_cart(Integer m_id){
				
		Boolean ch = cart.containsKey(m_id);		
		if(ch){
			Integer old = cart.get(m_id);
			cart.put(m_id,old+1);
		}
		else {
			cart.put(m_id, 1);
		}		
	}
	
	public boolean isEmpty(){
		
		return cart.isEmpty();		
	}

	public HashMap<Integer, Integer> get_cart(){		
		return cart;
	}
	
	public void remove_from_cart(Integer m_id){
		
		Boolean ch = cart.containsKey(m_id);
		if(ch)
		cart.remove(m_id);	
		
	}
	
	public void modify_cart(Integer m_id, Integer val){
		
		Boolean ch = cart.containsKey(m_id);
		if(ch)
		cart.put(m_id,val);		
	}
	
	public void emptyCart(){		
		cart.clear();		
	}
	
	
}
