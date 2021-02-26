package es.code.urjc.periftech.models;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(mappedBy="carroCliente")
	private Cliente cliente;
	@OneToMany(mappedBy="carroProducto",cascade=CascadeType.ALL)
	private List<Producto> productos;
	private float coste;
	@OneToOne(cascade=CascadeType.ALL)
	private Pedido pedidoCarrito;

	protected Cart() {}

	public Cart(Cliente cliente, List<Producto> productos, float coste, Pedido pedidoCarrito) {
		super();
		this.cliente = cliente;
		this.productos = productos;
		this.coste = coste;
		this.pedidoCarrito = pedidoCarrito;
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public float getCoste() {
		return coste;
	}

	public void setCoste(float coste) {
		this.coste = coste;
	}

	public Pedido getPedidoCarrito() {
		return pedidoCarrito;
	}

	public void setPedidoCarrito(Pedido pedidoCarrito) {
		this.pedidoCarrito = pedidoCarrito;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", cliente=" + cliente + ", productos=" + productos + ", coste=" + coste
				+ ", pedidoCarrito=" + pedidoCarrito + "]";
	}
}