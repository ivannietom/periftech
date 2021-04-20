package es.code.urjc.periftech.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne(mappedBy="pedidoCliente")
	private Cliente cliente;
	@OneToOne(mappedBy="pedidoCarrito")
	private Cart carroPedido;
	private float coste;
	
	protected Pedido() {}

	public Pedido(Cliente cliente, Cart carroPedido, float coste) {
		super();
		this.cliente = cliente;
		this.carroPedido = carroPedido;
		this.coste = coste;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cart getCarroPedido() {
		return carroPedido;
	}

	public void setCarroPedido(Cart carroPedido) {
		this.carroPedido = carroPedido;
	}

	public float getCoste() {
		return coste;
	}

	public void setCoste(float coste) {
		this.coste = coste;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", cliente=" + cliente + ", carritoPedido=" + carroPedido + ", coste=" + coste
				+ "]";
	}
}