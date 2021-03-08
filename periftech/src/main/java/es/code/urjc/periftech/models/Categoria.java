package es.code.urjc.periftech.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombreCategoria;
	@OneToMany(mappedBy="categoria",cascade=CascadeType.ALL)
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
		return "Categoria [id=" + id + ", nombre=" + nombreCategoria + ", productos=" + productos + "]";
	}
}