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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import es.code.urjc.periftech.models.*;
import es.code.urjc.periftech.repositories.*;
import es.code.urjc.periftech.services.*;

@Controller
public class RouteController {
	// Repositorios
	@Autowired
	private ClienteRepository clientes;
	@Autowired
	private CartRepository carros;
	@Autowired
	private ProductoRepository productos;
	@Autowired
	private PedidoRepository pedidos;

	// Servicios
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private CartService cartService;

	@PostConstruct
	public void init() {

	}

	@GetMapping("/")
	public String Index(Model model) {
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		return "index"; 
	}

	// CART
	
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
	// CATEGORIA

	@GetMapping("/categorias")
	public String mostrarCategorias(Model model, Pageable pageable) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 

		Page<Categoria> categorias = categoriaService.findAll(pageable);

		model.addAttribute("categorias", categorias);
		model.addAttribute("hasPrev", categorias.hasPrevious());
		model.addAttribute("hasNext", categorias.hasNext()); 
		model.addAttribute("nextPage", categorias.getNumber() + 1);
		model.addAttribute("prevPage", categorias.getNumber() - 1);

		return "ver-categorias";
	}

	@GetMapping("/nueva-categoria")
	public String nuevaCategoria(Model model, Pageable pageable) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		Page<Categoria> categoriasActuales = categoriaService.findAll(pageable);
		model.addAttribute("categoriasActuales", categoriasActuales);
		
		boolean esAdmin = esAdmin();
		model.addAttribute("esAdmin", esAdmin);
		
		return "nueva-categoria";
	}

	@RequestMapping("/agregarCategoria")
	public String agregarCategoria(Model model, @RequestParam String nombreCategoria, Pageable pageable) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		categoriaService.save(new Categoria(nombreCategoria, null));
		model.addAttribute("nombreCategoria", nombreCategoria);
		
		Page<Categoria> categoriasActuales = categoriaService.findAll(pageable);
		model.addAttribute("categoriasActuales", categoriasActuales);

		boolean esAdmin = esAdmin();
		model.addAttribute("esAdmin", esAdmin);

		return "nueva-categoria";
	}

	@GetMapping("/categoria/{id}")
	public String mostrarCategoria(Model model, @PathVariable long id, Pageable pageable) {

		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		Categoria categoria = categoriaService.findById(id).orElseThrow();

		model.addAttribute("categoria", categoria);

		Page<Producto> productos = productoService.findBycategoria(categoria, pageable);

		model.addAttribute("productos", productos);
		model.addAttribute("hasPrev", productos.hasPrevious());
		model.addAttribute("hasNext", productos.hasNext());
		model.addAttribute("nextPage", productos.getNumber() + 1);
		model.addAttribute("prevPage", productos.getNumber() - 1);

		return "ver-productos";
	}
	@GetMapping("/eliminarCategoria/{id}")
	public String eliminarCategoria(Model model, @PathVariable long id, Pageable pageable) {
		
		boolean esAdmin = esAdmin();
		model.addAttribute("esAdmin", esAdmin);
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		Categoria categoriaEliminada = categoriaService.findById(id).orElseThrow();
		categoriaService.deleteById(id);
		
		model.addAttribute("categoriaEliminada", categoriaEliminada);
			
		return "categoria-eliminada";
	}

	// CLIENTE
	
	@GetMapping("/login")
	public String Login(Model model) {
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		return "login";
	}
	
	@GetMapping("/logout")
	public String Logout(Model model) {
		clienteService.setClienteActual(null);
		clienteService.setEstaLogeado(false);
		return "logout";
	}
	
	@GetMapping("/register") 
	public String Register(Model model) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		return "register"; 
	}
	
	@GetMapping("/mi-perfil")
	public String miPerfil(Model model) {
		
		boolean esAdmin = esAdmin();
		model.addAttribute("esAdmin", esAdmin);
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		boolean esNormal = false;
		boolean esUsuarioAdmin = false; 
		boolean esPremium = false;
		
		Cliente clientePerfil = clienteService.getClienteActual(); 
		int tipoCliente = clientePerfil.getTipoCliente();
		if(tipoCliente == 0) esAdmin = true;
		if(tipoCliente == 1) esPremium = true;
		if(tipoCliente == 2) esNormal = true;
		
		model.addAttribute("esUsuarioAdmin", esUsuarioAdmin);
		model.addAttribute("esPremium", esPremium);
		model.addAttribute("esNormal", esNormal);
		model.addAttribute("clientePerfil", clientePerfil);
		
		return "mi-perfil";
	}
	
	@RequestMapping("/registro")
	public String Registro(Model model, @RequestParam String nombreCompleto, @RequestParam String nombreUsuario,
			@RequestParam String email, @RequestParam String password, @RequestParam String direccion) {

		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		model.addAttribute("nombreCompleto", nombreCompleto);
		model.addAttribute("nombreUsuario", nombreUsuario);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("direccion", direccion); 

		Cliente cliente = new Cliente(nombreCompleto, nombreUsuario, email, password, direccion, null, null, 2);
		clientes.save(cliente);
		return "registro-correcto";
	}

	@RequestMapping("/esLoginCorrecto")
	public String comprobarLogin(Model model, @RequestParam String nombreUsuario, @RequestParam String password) {
		
		boolean existe = false;
		Cliente c = clientes.findByNombreUsuario(nombreUsuario);
		if (c != null && c.getPassword().equals(password)) {
			existe = true;
			clienteService.setClienteActual(c);
			clienteService.setEstaLogeado(true);
		}
		if (existe) {
			if (clienteService.getClienteActual().getCarroCliente() == null) {
				List<Producto> listaProductos = new ArrayList<Producto>();
				Cart carrito = new Cart(clienteService.getClienteActual(), listaProductos, 0, null);
				clienteService.getClienteActual().setCarroCliente(carrito);
				carros.save(carrito);
				clientes.save(clienteService.getClienteActual());
				boolean esAdmin = esAdmin();
				model.addAttribute("esAdmin", esAdmin);
			}
		} 

		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 

		model.addAttribute("esLoginCorrecto", existe);
		return "comprobar-login";
	}

	// PEDIDO
	
	@GetMapping("/realizarPedido")
	public String realizarPedido(Model model, Pageable pageable) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		float costeCarro = clienteService.getClienteActual().getCarroCliente().getCoste();
	
		Random r = new Random();
		DecimalFormat df = new DecimalFormat("#.##");
		float costeEnvio = 0;
		
		float costePedido = 0;
		if (clienteService.getClienteActual().getTipoCliente() == 2) {
			costeEnvio = r.nextFloat() * 5;
			costePedido = costeCarro + costeEnvio;
		} else {
			costeEnvio = 0;
			costePedido = costeCarro; 
		}
		String costeEnvioFormateado = df.format(costeEnvio); 
		
		// float costePedido = costeCarro + costeEnvio;
		String costePedidoFormateado = df.format(costePedido);

		String direccionCliente = clienteService.getClienteActual().getDireccion();

		model.addAttribute("costePedidoFormateado", costePedidoFormateado);
		model.addAttribute("costeCarro", costeCarro);
		model.addAttribute("costeEnvioFormateado", costeEnvioFormateado);
		model.addAttribute("direccionCliente", direccionCliente); 

		Pedido pedidoCliente = new Pedido(clienteService.getClienteActual(),
		clienteService.getClienteActual().getCarroCliente(), costePedido);
		clienteService.getClienteActual().setPedidoCliente(pedidoCliente);
		clienteService.getClienteActual().getCarroCliente().setPedidoCarrito(pedidoCliente);
	
		Producto productoPremium = productoService.findByNombreProducto("Suscripción premium");
		boolean esPremium = clienteService.getClienteActual().getCarroCliente().getProductos()
				.contains(productoPremium);
		if (esPremium) {
			clienteService.getClienteActual().setTipoCliente(1);
		}

		Cart cartActual = clienteService.getClienteActual().getCarroCliente();
		Page<Producto> listaProductos = productoService.findByCarroProducto(cartActual, pageable);
		cartActual.setCoste(0);
		for(Producto p : listaProductos) {
			cartActual.getProductos().remove(p);
			p.setCarroProducto(null);
		}
		
	
		pedidos.save(pedidoCliente);
		clientes.save(clienteService.getClienteActual());
		carros.save(clienteService.getClienteActual().getCarroCliente());
		return "realizar-pedido";
	}

	// PRODUCTO

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

	// Varios
	
	public boolean esAdmin() {
		int tipoCliente = clienteService.getClienteActual().getTipoCliente(); 
		return tipoCliente == 0;
	}
}