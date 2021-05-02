package es.code.urjc.periftech.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Cliente;
import es.code.urjc.periftech.models.Producto;
import es.code.urjc.periftech.repositories.CartRepository;
import es.code.urjc.periftech.repositories.ClienteRepository;
import es.code.urjc.periftech.services.ClienteService;

@Controller
public class ClienteController {
	
	//Repositorios
	@Autowired
	private ClienteRepository clientes;
	@Autowired
	private CartRepository carros;
	
	
	//Servicios
	@Autowired
	private ClienteService clienteService;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
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

		Cliente cliente = new Cliente(nombreCompleto, nombreUsuario, email, password, direccion, null, null, 2,passwordEncoder.encode(password), "USER");
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
	
	public boolean esAdmin() {
		int tipoCliente = clienteService.getClienteActual().getTipoCliente(); 
		return tipoCliente == 0;
	}
}