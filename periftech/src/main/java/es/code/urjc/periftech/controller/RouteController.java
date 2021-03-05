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

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import es.code.urjc.periftech.models.*;
import es.code.urjc.periftech.repositories.*;
import es.code.urjc.periftech.services.*;

@Controller
public class RouteController {

	//Repositorios
	@Autowired
	private ClienteRepository clientes;
	@Autowired
	private CartRepository carros;
	@Autowired
	private ProductoRepository productos;
	
	//Servicios
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
		return "index";
	}

	@GetMapping("/login")
	public String Login(Model model) {
		return "login";
	}

	@GetMapping("/categorias")
	public String mostrarCategorias(Model model, HttpSession session, Pageable pageable) {

		Page<Categoria> categorias = categoriaService.findAll(pageable);
		
		model.addAttribute("categorias", categorias);
		model.addAttribute("hasPrev", categorias.hasPrevious());
		model.addAttribute("hasNext", categorias.hasNext());
		model.addAttribute("nextPage", categorias.getNumber()+1);
		model.addAttribute("prevPage", categorias.getNumber()-1);		
		
		model.addAttribute("welcome", session.isNew());
		return "ver-categorias";
	}

	@GetMapping("/cart")
	public String Cart(Model model) {
		return "cart";
	}

	@GetMapping("/register")
	public String Register(Model model) {
		return "register";
	}

	@RequestMapping("/busqueda")
	public String Busqueda(Model model, @RequestParam String productoBuscado) {
		model.addAttribute("busqueda", productoBuscado);
		return "producto-busqueda";
	}

	@RequestMapping("/esLoginCorrecto")
	public String comprobarLogin(Model model, @RequestParam String nombreUsuario, @RequestParam String password) {

		boolean existe = false;
		Cliente c = clientes.findByNombreUsuario(nombreUsuario);
		if (c != null && c.getPassword().equals(password))
			existe = true;
		model.addAttribute("esLoginCorrecto", existe);
		return "comprobar-login";
	}

	@RequestMapping("/registro")
	public String Registro(Model model, @RequestParam String nombreCompleto, @RequestParam String nombreUsuario,
			@RequestParam String email, @RequestParam String password, @RequestParam String direccion) {

		model.addAttribute("nombreCompleto", nombreCompleto);
		model.addAttribute("nombreUsuario", nombreUsuario);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("direccion", direccion);

		Cliente cliente = new Cliente(nombreCompleto, nombreUsuario, email, password, direccion, null, null);
		clientes.save(cliente);
		return "registro-correcto";
	}
	
	@GetMapping("/categoria/{id}")
	public String mostrarCategoria(Model model, @PathVariable long id, Pageable pageable) {

		Categoria categoria = categoriaService.findById(id).orElseThrow();

		model.addAttribute("categoria", categoria);
		
		Page<Producto> productos = productoService.findAll(pageable);
		
		model.addAttribute("productos", productos);
		model.addAttribute("hasPrev", productos.hasPrevious());
		model.addAttribute("hasNext", productos.hasNext());
		model.addAttribute("nextPage", productos.getNumber()+1);
		model.addAttribute("prevPage", productos.getNumber()-1);

		return "ver-productos";
	}
}
