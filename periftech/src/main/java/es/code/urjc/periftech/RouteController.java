package es.code.urjc.periftech;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {
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
	
	@RequestMapping("/registro")
	public String Registro(Model model, @RequestParam String nombreCompleto, 
			@RequestParam String email, 
			@RequestParam String password) {
		model.addAttribute("nombre", nombreCompleto);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		return "registro-correcto";
	}
}
