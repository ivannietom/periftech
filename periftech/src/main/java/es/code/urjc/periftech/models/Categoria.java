package es.code.urjc.periftech.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombreCategoria;
	
	@JsonIgnore
	@OneToMany(mappedBy="categoria",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Producto> productos;
	
	protected Categoria() {}

	public Categoria(String nombre, List<Producto> productos) {
		super();
		this.nombreCategoria = nombre;
		this.productos = productos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombreCategoria;
	}

	public void setNombre(String nombre) {
		this.nombreCategoria = nombre;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return nombreCategoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombreCategoria == null) ? 0 : nombreCategoria.hashCode());
		result = prime * result + ((productos == null) ? 0 : productos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombreCategoria == null) {
			if (other.nombreCategoria != null)
				return false;
		} else if (!nombreCategoria.equals(other.nombreCategoria))
			return false;
		if (productos == null) {
			if (other.productos != null)
				return false;
		} else if (!productos.equals(other.productos))
			return false;
		return true;
	}
}
