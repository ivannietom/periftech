package es.code.urjc.periftech.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.code.urjc.periftech.models.Cliente;
import es.code.urjc.periftech.repositories.ClienteRepository;

@Service
public class ClienteService {
	

	public Cliente clienteActual;
	public boolean estaLogeado;
	@Autowired
	private ClienteRepository clientes;
	
	@PostConstruct
	public void init() {
		save(new Cliente("admin", "admin", "admin@periftech.com", "1234", "-", null, null, 0));
		save(new Cliente("cliente normal", "a", "00ivannieto@gmail.com", "a", "c/Cliente", null, null, 2));
	}

	public Cliente getClienteActual() {
		String nombre = clienteActual.getNombreUsuario();
		return clientes.findByNombreUsuario(nombre);
	}

	public void setClienteActual(Cliente c) {
		clienteActual = c;
	}
	
	public void save(Cliente cliente) {
		clientes.save(cliente);
	}

	public boolean estaLogeado() {
		return estaLogeado;
	}

	public void setEstaLogeado(boolean estaLogeado) {
		this.estaLogeado = estaLogeado;
	}
	
}
