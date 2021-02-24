package es.code.urjc.periftech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.code.urjc.periftech.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
