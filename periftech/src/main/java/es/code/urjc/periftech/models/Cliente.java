package es.code.urjc.periftech.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	@OneToOne(cascade=CascadeType.ALL)
	private Cart carroCliente;
	@OneToOne(cascade=CascadeType.ALL)
	private Pedido pedidoCliente;
	private int tipoCliente; // 0 = admin, 1 = premium, 2 = normal
	
	
	protected Cliente() {}


	public Cliente(String nombreCompleto, String nombreUsuario, String correo, String password, String direccion,
			Cart carroCliente, Pedido pedidoCliente, int tipoCliente) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
		this.password = password;
		this.direccion = direccion;
		this.carroCliente = carroCliente;
		this.pedidoCliente = pedidoCliente;
		this.tipoCliente = tipoCliente;
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


	public Cart getCarroCliente() {
		return carroCliente;
	}


	public void setCarroCliente(Cart carroCliente) {
		this.carroCliente = carroCliente;
	}


	public Pedido getPedidoCliente() {
		return pedidoCliente;
	}


	public void setPedidoCliente(Pedido pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}
	
	public int getTipoCliente() {
		return tipoCliente;
	}


	public void setTipoCliente(int tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombreCompleto=" + nombreCompleto + ", nombreUsuario=" + nombreUsuario
				+ ", correo=" + correo + ", password=" + password + ", direccion=" + direccion + ", carritocliente="
				+ carroCliente + ", pedidoCliente=" + pedidoCliente + "]";
	}
}
