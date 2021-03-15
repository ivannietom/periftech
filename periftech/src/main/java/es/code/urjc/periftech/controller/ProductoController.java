package es.code.urjc.periftech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Categoria;
import es.code.urjc.periftech.models.Producto;
import es.code.urjc.periftech.repositories.CartRepository;
import es.code.urjc.periftech.repositories.ProductoRepository;
import es.code.urjc.periftech.services.CategoriaService;
import es.code.urjc.periftech.services.ClienteService;
import es.code.urjc.periftech.services.ProductoService;

@Controller
public class ProductoController {
	
	//Repositorios
	@Autowired
	private CartRepository carros;
	@Autowired
	private ProductoRepository productos;
	
	//Servicios
	@Autowired
	private ProductoService productoService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping("/categoria/producto/agregarProducto{id}")
	public String agregarProductoCarro(Model model, @PathVariable long id) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		if(clienteService.estaLogeado) {
			Producto productoActual = productos.findById(id).orElseThrow();
			Cart carrito = clienteService.getClienteActual().getCarroCliente();
	
			carrito.getProductos().add(productoActual);
			productoActual.setCarroProducto(carrito);
	
			float costeCarroActual = carrito.getCoste();
			float costeActualizado = costeCarroActual + productoActual.getPrecio();
			carrito.setCoste(costeActualizado);
	
			productos.save(productoActual);
			carros.save(carrito);
			
			model.addAttribute("productoActual", productoActual);
	
			return "producto-agregado-cart";
		}else return "hacer-login"; 
	}

	@GetMapping("/nuevo-producto")
	public String nuevoProducto(Model model, Pageable pageable) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		boolean esAdmin = esAdmin();
		model.addAttribute("esAdmin", esAdmin);

		Page<Producto> productosActuales = productoService.findAll(pageable);
		model.addAttribute("productosActuales", productosActuales);
		
		return "nuevo-producto";
	}

	@RequestMapping("/agregarProducto")
	public String agregarProducto(Model model, @RequestParam String nombreProducto, @RequestParam String precioProducto,
			@RequestParam String categoriaProducto,Pageable pageable) { 
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		float precioProductoFloat = Float.parseFloat(precioProducto);
		Categoria categoriaProductoAgregar = categoriaService.findByNombreCategoria(categoriaProducto);
		productoService.save(new Producto(nombreProducto, precioProductoFloat, categoriaProductoAgregar, null));
		

		Page<Producto> productosActuales = productoService.findAll(pageable);
		model.addAttribute("productosActuales", productosActuales);

		model.addAttribute("nombreProducto", nombreProducto);
		model.addAttribute("precioProductoFloat", precioProductoFloat);
		model.addAttribute("categoriaProductoAgregar", categoriaProductoAgregar);
 
		boolean esAdmin = esAdmin();
		model.addAttribute("esAdmin", esAdmin);

		return "nuevo-producto";
	}
	
	@GetMapping("/eliminarProducto/{id}")
	public String eliminarProducto(Model model, @PathVariable long id, Pageable pageable) {
		
		boolean esAdmin = esAdmin();
		model.addAttribute("esAdmin", esAdmin);
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		Producto productoEliminado = productoService.findById(id).orElseThrow();
		productoService.deleteById(id);
		
		model.addAttribute("productoEliminado", productoEliminado);
			
		return "producto-eliminado";
	}
	
	@GetMapping("/categoria/producto/{id}")
	public String mostrarProducto(Model model, @PathVariable long id, Pageable pageable) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		Producto producto = productoService.findById(id).orElseThrow();

		model.addAttribute("producto", producto);

		/*
		 * Page<Producto> productos = productoService.findAll(pageable);
		 * 
		 * model.addAttribute("productos", productos); model.addAttribute("hasPrev",
		 * productos.hasPrevious()); model.addAttribute("hasNext", productos.hasNext());
		 * model.addAttribute("nextPage", productos.getNumber()+1);
		 * model.addAttribute("prevPage", productos.getNumber()-1);
		 */

		return "ver-producto";
	}
	
	@RequestMapping("/busqueda")
	public String Busqueda(Model model, @RequestParam String productoBuscado) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		Producto productoEncontrado = productoService.findByNombreProducto(productoBuscado);
		
		model.addAttribute("productoBuscado",productoBuscado);
		model.addAttribute("productoEncontrado", productoEncontrado);
		
		return "producto-busqueda";
	}
	
	public boolean esAdmin() {
		int tipoCliente = clienteService.getClienteActual().getTipoCliente(); 
		return tipoCliente == 0;
	}


}
