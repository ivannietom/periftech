package es.code.urjc.periftech.models;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Cliente cliente;
	private Producto[] productos;
	private float coste;
	private String fechaEntrega;
	
	public Pedido() {}

	public Pedido(Cliente cliente, Producto[] productos, float coste, String fechaEntrega) {
		super();
		this.cliente = cliente;
		this.productos = productos;
		this.coste = coste;
		this.fechaEntrega = fechaEntrega;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Producto[] getProductos() {
		return productos;
	}

	public void setProductos(Producto[] productos) {
		this.productos = productos;
	}

	public float getCoste() {
		return coste;
	}

	public void setCoste(float coste) {
		this.coste = coste;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", cliente=" + cliente + ", productos=" + Arrays.toString(productos) + ", coste="
				+ coste + ", fechaEntrega=" + fechaEntrega + "]";
	}
}