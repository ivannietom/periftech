package es.code.urjc.periftech.repositories;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.code.urjc.periftech.models.Categoria;

@CacheConfig(cacheNames = "categorias")
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	@CacheEvict(allEntries = true)
	Categoria save(Categoria categoria);
	
	Categoria findByNombreCategoria(String nombreCategoria);
	
	@Cacheable
	Page<Categoria> findAll(Pageable page);
}
