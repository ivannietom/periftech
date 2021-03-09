package es.code.urjc.periftech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Categoria;
import es.code.urjc.periftech.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
	Page<Producto> findByCategoria(Categoria categoria,Pageable page);
	Page<Producto> findByCarroProducto(Cart carroProducto,Pageable page);
	Producto findByNombreProducto(String nombreProducto);
}
