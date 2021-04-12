package es.code.urjc.periftech.services;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.code.urjc.periftech.repositories.CategoriaRepository;
import es.code.urjc.periftech.repositories.ProductoRepository;
import es.code.urjc.periftech.models.Categoria;
import es.code.urjc.periftech.models.Producto;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categorias;
	@Autowired
	private ProductoRepository productos;

	@PostConstruct
	public void init() {
		Categoria teclados = new Categoria("Teclados", null);
		save(teclados);
		Categoria ratones = new Categoria("Ratones", null);
		save(ratones);
		Categoria pantallas = new Categoria("Pantallas", null);
		save(pantallas);
		Categoria premium = new Categoria("Premium", null);
		save(premium);
		productos.save(new Producto("Teclado 1", 20.2f, teclados, null));
		productos.save(new Producto("Teclado 2", 40.5f,teclados, null));
		productos.save(new Producto("Ratón 1", 10.99f, ratones, null));	
		productos.save(new Producto("Ratón 2", 25.82f, ratones, null));
		productos.save(new Producto("Pantalla 1", 199.99f, pantallas, null));
		productos.save(new Producto("Pantalla 2", 499.99f, pantallas, null));
		productos.save(new Producto("Suscripción premium", 5.99f, premium, null));
	}

	public Collection<Categoria> findAll() {
		return categorias.findAll();
	}
	
	public Page<Categoria> findAll(Pageable pageable) {
		return categorias.findAll(pageable);
	}
	
	public Categoria findByNombreCategoria(String nombreCategoria){
		return categorias.findByNombreCategoria(nombreCategoria);
	}

	public Optional<Categoria> findById(long id) {
		return categorias.findById(id);
	}

	public void save(Categoria categoria) {
		categorias.save(categoria);
	}
	
	public void deleteById(long id) {
		categorias.deleteById(id);
	}
}

