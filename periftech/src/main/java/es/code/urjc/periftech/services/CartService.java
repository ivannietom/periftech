package es.code.urjc.periftech.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Cliente;
import es.code.urjc.periftech.repositories.CartRepository;
import es.code.urjc.periftech.repositories.ProductoRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository carros;

     public Cart findBycliente(Cliente cliente){
         return carros.findBycliente(cliente);
     }
}
