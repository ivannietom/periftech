package es.code.urjc.periftech.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Categoria;
import es.code.urjc.periftech.models.Producto;
import es.code.urjc.periftech.repositories.CategoriaRepository;
import es.code.urjc.periftech.repositories.ProductoRepository;

@Service
public class ProductoService {
	@Autowired
	private ProductoRepository productos;
	
	@Autowired
	private CategoriaRepository categorias;

	@PostConstruct
	public void init() {
		save(new Producto("Teclado 1", 20.2f, categorias.findBynombreCategoria("Teclados"), null, 20));
		save(new Producto("Teclado 2", 40.5f,categorias.findBynombreCategoria("Teclados"), null, 20));
		save(new Producto("Ratón 1", 10.99f, categorias.findBynombreCategoria("Ratones"), null, 20));	
		save(new Producto("Ratón 2", 25.82f, categorias.findBynombreCategoria("Ratones"), null, 20));
		save(new Producto("Pantalla 1", 199.99f, categorias.findBynombreCategoria("Pantallas"), null, 20));
		save(new Producto("Pantalla 2", 499.99f, categorias.findBynombreCategoria("Pantallas"), null, 20));
	}

	public List<Producto> findAll() {
		return productos.findAll();
	}
	public Page<Producto> findBycategoria(Categoria categoria,Pageable pageable){
		return productos.findByCategoria(categoria,pageable);
	}
	public Page<Producto> findByCarroProducto(Cart carroProducto,Pageable pageable){
		return productos.findByCarroProducto(carroProducto,pageable);
	}
	public Page<Producto> findAll(Pageable pageable) {
		return productos.findAll(pageable);
	}

	public Optional<Producto> findById(long id) {
		return productos.findById(id);
	}

	public void save(Producto producto) {
		productos.save(producto);
	}
}