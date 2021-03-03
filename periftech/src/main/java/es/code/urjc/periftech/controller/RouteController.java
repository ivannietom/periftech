package es.code.urjc.periftech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import javax.annotation.PostConstruct;

import es.code.urjc.periftech.models.*;
import es.code.urjc.periftech.repositories.CartRepository;
import es.code.urjc.periftech.repositories.ClienteRepository;

@Controller
public class RouteController {
	
	@Autowired
	private ClienteRepository clientes;
	@Autowired
	private CartRepository carros;
	
	@PostConstruct
	public void init() {
		Cliente a = new Cliente("Manu", "Manu", "Manu@gmail.com", "contra", "calle1", null, null);
		Cart b = new Cart(a,null,4.3f,null);
		Cart c = new Cart(null,null,4.3f,null);
		a.setCarroCliente(b);
		clientes.save(a);
		carros.save(b);
		carros.save(c);
	}
	
	@GetMapping("/")
	public String Index(Model model) {
		return "index";
	}
	
	@GetMapping("/login")
	public String Login(Model model) {
		return "login";
	}
	
	@GetMapping("/categories")
	public String Categories(Model model) {
		return "categories";
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
	public String comprobarLogin(Model model,
			@RequestParam String nombreUsuario,
			@RequestParam String password) {
		
		boolean existe = false;
		Cliente c = clientes.findByNombreUsuario(nombreUsuario);
		if(c != null && c.getPassword().equals(password)) existe = true;
		model.addAttribute("esLoginCorrecto",existe);
		return "comprobar-login";
	}
	
	@RequestMapping("/registro")
	public String Registro(Model model, @RequestParam String nombreCompleto,
			@RequestParam String nombreUsuario,  
			@RequestParam String email, 
			@RequestParam String password,
			@RequestParam String direccion) {
				
		model.addAttribute("nombreCompleto", nombreCompleto);
		model.addAttribute("nombreUsuario", nombreUsuario);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("direccion", direccion);
		
		Cliente c1 = new Cliente(nombreCompleto,nombreUsuario,email,password,direccion,null,null);
		clientes.save(c1);
		return "registro-correcto";
	}
	
	@PostMapping("/cliente/{id}")
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente){
		clientes.save(cliente);
		
		java.net.URI location = fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();

		return ResponseEntity.created(location).body(cliente);
	}
}
