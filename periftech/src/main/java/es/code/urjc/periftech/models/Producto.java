package es.code.urjc.periftech.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombreProducto;
	private float precio;
	@ManyToOne
	private Categoria categoria;
	@ManyToOne
	private Cart carroProducto;
	
	protected Producto() {}

	public Producto(String nombre, float precio, Categoria categoria, Cart carroProducto) {
		super();
		this.nombreProducto = nombre;
		this.precio = precio;
		this.categoria = categoria;
		this.carroProducto = carroProducto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombreProducto;
	}

	public void setNombre(String nombre) {
		this.nombreProducto = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Cart getCarroProducto() {
		return carroProducto;
	}

	public void setCarroProducto(Cart carroProducto) {
		this.carroProducto = carroProducto;
	}


	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombreProducto + ", precio=" + precio + ", categoria=" + categoria
				+ ", carroProducto=" + carroProducto + "]";
	}
}