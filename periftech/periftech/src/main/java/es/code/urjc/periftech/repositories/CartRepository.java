package es.code.urjc.periftech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Cliente;

public interface CartRepository extends JpaRepository<Cart, Long>{
	Cart findBycliente(Cliente cliente);
}
