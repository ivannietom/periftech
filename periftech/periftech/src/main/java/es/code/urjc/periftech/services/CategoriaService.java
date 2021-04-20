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
	private ProductoRepository pr;

	@PostConstruct
	public void init() {
		Categoria c1 = new Categoria("Teclados", null);
		save(c1);
		Categoria c2 = new Categoria("Ratones", null);
		save(c2);
		Categoria c3 = new Categoria("Pantallas", null);
		save(c3);
		Categoria c4 = new Categoria("Premium", null);
		save(c4);
		
		pr.save(new Producto("Teclado 1", 20.2f,c1, null));
		pr.save(new Producto("Teclado 2", 40.5f,c1, null));
		pr.save(new Producto("Ratón 1", 10.99f, c2, null));	
		pr.save(new Producto("Ratón 2", 25.82f, c2, null));
		pr.save(new Producto("Pantalla 1", 199.99f, c3, null));
		pr.save(new Producto("Pantalla 2", 499.99f, c3, null));
		pr.save(new Producto("Suscripción premium", 5.99f, c4, null));
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

