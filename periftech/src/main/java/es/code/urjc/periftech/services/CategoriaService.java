package es.code.urjc.periftech.services;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.code.urjc.periftech.repositories.CategoriaRepository;

import es.code.urjc.periftech.models.Categoria;
@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categorias;

	@PostConstruct
	public void init() {
		save(new Categoria("Teclados", null));
		save(new Categoria("Ratones", null));
		save(new Categoria("Pantallas", null));
	}

	public Collection<Categoria> findAll() {
		return categorias.findAll();
	}
	
	public Page<Categoria> findAll(Pageable pageable) {
		return categorias.findAll(pageable);
	}

	public Optional<Categoria> findById(long id) {
		return categorias.findById(id);
	}

	public void save(Categoria categoria) {
		categorias.save(categoria);
	}
}

