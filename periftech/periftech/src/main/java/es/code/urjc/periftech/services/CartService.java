package es.code.urjc.periftech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Cliente;
import es.code.urjc.periftech.models.Producto;
import es.code.urjc.periftech.repositories.CartRepository;

@Service
public class CartService {
	@Autowired
	private CartRepository carros;
	
	public Cart findBycliente(Cliente cliente){
		 return carros.findBycliente(cliente);
	}
	
	public void save(Cart cart) {
		carros.save(cart);
	}
}
