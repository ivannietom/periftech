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

import es.code.urjc.periftech.models.Categoria;
import es.code.urjc.periftech.models.Producto;
import es.code.urjc.periftech.services.CategoriaService;
import es.code.urjc.periftech.services.ClienteService;
import es.code.urjc.periftech.services.ProductoService;

@Controller
public class CategoriaController {
	
	//Servicios
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ProductoService productoService;
	
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
	
	public boolean esAdmin() {
		int tipoCliente = clienteService.getClienteActual().getTipoCliente(); 
		return tipoCliente == 0;
	}


}
