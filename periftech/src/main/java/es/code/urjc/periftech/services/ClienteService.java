package es.code.urjc.periftech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.code.urjc.periftech.models.Cliente;
import es.code.urjc.periftech.repositories.ClienteRepository;

@Service
public class ClienteService {
	public Cliente clienteActual;
    @Autowired
    private ClienteRepository clientes;
    
    public Cliente getClienteActual() {
        String nombre = clienteActual.getNombreUsuario();
        return clientes.findByNombreUsuario(nombre);
    }

    public void setClienteActual(Cliente c) {
        clienteActual = c;
    }
}
