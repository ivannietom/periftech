package es.code.urjc.periftech.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Cliente;
import es.code.urjc.periftech.models.Producto;
import es.code.urjc.periftech.repositories.ClienteRepository;
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
	@Autowired
    private ClienteRepository clienteRepository;

	
	@GetMapping("/cart")
	public String Cart(Model model, Pageable pageable, HttpServletRequest request) { 
		float totalCarro = 0;
		
		String name = request.getUserPrincipal().getName();

        boolean existe = false;
        Cliente c = clienteRepository.findByNombreUsuario(name);
        if (c != null ) {
            existe = true;
            clienteService.setClienteActual(c);
            clienteService.setEstaLogeado(true);
        }
        if (existe) {
            if (clienteService.getClienteActual().getCarroCliente() == null) {
                List<Producto> listaProductos = new ArrayList<Producto>();
                Cart carrito = new Cart(clienteService.getClienteActual(), listaProductos, 0, null);
                clienteService.getClienteActual().setCarroCliente(carrito);
                cartService.save(carrito);
                clienteRepository.save(clienteService.getClienteActual());
                boolean esAdmin = esAdmin();
                model.addAttribute("esAdmin", esAdmin);
            }
        }
        
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
	
	public boolean esAdmin() {
        int tipoCliente = clienteService.getClienteActual().getTipoCliente(); 
        return tipoCliente == 0;
    }

}
