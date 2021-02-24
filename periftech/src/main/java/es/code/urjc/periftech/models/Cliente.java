package es.code.urjc.periftech.models;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombreCompleto;
	private String nombreUsuario;
	private String correo;
	private String password;
	private String direccion;
	private Cart carroCompra;
	private Pedido[] pedidos;
	
	public Cliente() {}

	public Cliente(String nombreCompleto, String nombreUsuario, String correo, String password,
			String direccion, Cart carroCompra, Pedido[] pedidos) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
		this.password = password;
		this.direccion = direccion;
		this.carroCompra = carroCompra;
		this.pedidos = pedidos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Cart getCarroCompra() {
		return carroCompra;
	}

	public void setCarroCompra(Cart carroCompra) {
		this.carroCompra = carroCompra;
	}

	public Pedido[] getPedidos() {
		return pedidos;
	}

	public void setPedidos(Pedido[] pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombreCompleto=" + nombreCompleto + ", nombreUsuario=" + nombreUsuario
				+ ", correo=" + correo + ", password=" + password + ", direccion=" + direccion + ", carroCompra="
				+ carroCompra + ", pedidos=" + Arrays.toString(pedidos) + "]";
	}
}
