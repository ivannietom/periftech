package es.code.urjc.periftech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.code.urjc.periftech.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	Categoria findByNombreCategoria(String nombreCategoria);
}
