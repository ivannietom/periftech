package es.code.urjc.periftech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Producto;
import es.code.urjc.periftech.services.CartService;
import es.code.urjc.periftech.services.ClienteService;
import es.code.urjc.periftech.services.ProductoService;

@Controller
public class CartController {
	
	//Servicios
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private CartService cartService;

	
	@GetMapping("/cart")
	public String Cart(Model model, Pageable pageable) { 
		float totalCarro = 0;
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		if(clienteService.estaLogeado) {
			
			Cart carritoActual = clienteService.getClienteActual().getCarroCliente();
	
			Page<Producto> listaProductos = productoService.findByCarroProducto(carritoActual, pageable);
			totalCarro = carritoActual.getCoste();
			
			model.addAttribute("clienteEstaLogueado",clienteService.estaLogeado); 
			model.addAttribute("listaProductos", listaProductos);
			model.addAttribute("hasPrev", listaProductos.hasPrevious());
			model.addAttribute("hasNext", listaProductos.hasNext());
			model.addAttribute("nextPage", listaProductos.getNumber() + 1);
			model.addAttribute("prevPage", listaProductos.getNumber() - 1);
			model.addAttribute("totalCarro", totalCarro);
			
			return "cart";
		}else return "hacer-login"; 
	}
	
	@GetMapping("/eliminarProductoCarro/{id}")
	public String eliminarProductoCarro (Model model, @PathVariable long id, Pageable pageable) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		Cart carrito =  clienteService.getClienteActual().getCarroCliente();
		
		Producto productoAEliminar = productoService.findById(id).orElseThrow();
        carrito.getProductos().remove(productoAEliminar);
        productoAEliminar.setCarroProducto(null); 
        
        float costeCarroActual = carrito.getCoste();
		float costeActualizado = costeCarroActual - productoAEliminar.getPrecio();
		carrito.setCoste(costeActualizado);
		
        productoService.save(productoAEliminar);
        cartService.save(clienteService.getClienteActual().getCarroCliente());
        
        model.addAttribute("productoAEliminar", productoAEliminar);
        
		return "producto-eliminado-cart";
	}

}
