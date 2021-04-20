package es.code.urjc.periftech.controller;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.code.urjc.periftech.models.Cart;
import es.code.urjc.periftech.models.Pedido;
import es.code.urjc.periftech.models.Producto;
import es.code.urjc.periftech.repositories.CartRepository;
import es.code.urjc.periftech.repositories.ClienteRepository;
import es.code.urjc.periftech.repositories.PedidoRepository;
import es.code.urjc.periftech.services.ClienteService;
import es.code.urjc.periftech.services.ProductoService;
import es.code.urjc.periftech.servicioInterno.ServicioInterno;

@Controller
public class PedidoController {
	
	// Repositorios
	@Autowired
	private ClienteRepository clientes;
	@Autowired
	private CartRepository carros;
	@Autowired
	private PedidoRepository pedidos;
	
	//Servicios
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/realizarPedido")
	public String realizarPedido(Model model, Pageable pageable) {
		
		boolean estaLogeado = clienteService.estaLogeado;
		model.addAttribute("estaLogeado",estaLogeado); 
		
		float costeCarro = clienteService.getClienteActual().getCarroCliente().getCoste();
	
		Random r = new Random();
		DecimalFormat df = new DecimalFormat("#.##");
		float costeEnvio = 0;
		
		float costePedido = 0;
		if (clienteService.getClienteActual().getTipoCliente() == 2) {
			costeEnvio = r.nextFloat() * 5;
			costePedido = costeCarro + costeEnvio;
		} else {
			costeEnvio = 0;
			costePedido = costeCarro; 
		}
		String costeEnvioFormateado = df.format(costeEnvio); 
		
		// float costePedido = costeCarro + costeEnvio;
		String costePedidoFormateado = df.format(costePedido);

		String direccionCliente = clienteService.getClienteActual().getDireccion();

		model.addAttribute("costePedidoFormateado", costePedidoFormateado);
		model.addAttribute("costeCarro", costeCarro);
		model.addAttribute("costeEnvioFormateado", costeEnvioFormateado);
		model.addAttribute("direccionCliente", direccionCliente); 

		Pedido pedidoCliente = new Pedido(clienteService.getClienteActual(),
		clienteService.getClienteActual().getCarroCliente(), costePedido);
		clienteService.getClienteActual().setPedidoCliente(pedidoCliente);
		clienteService.getClienteActual().getCarroCliente().setPedidoCarrito(pedidoCliente);
	
		Producto productoPremium = productoService.findByNombreProducto("Suscripción premium");
		boolean esPremium = clienteService.getClienteActual().getCarroCliente().getProductos()
				.contains(productoPremium);
		if (esPremium) {
			clienteService.getClienteActual().setTipoCliente(1);
		}

		Cart cartActual = clienteService.getClienteActual().getCarroCliente();
		Page<Producto> listaProductos = productoService.findByCarroProducto(cartActual, pageable);
		cartActual.setCoste(0);
		for(Producto p : listaProductos) {
			cartActual.getProductos().remove(p);
			p.setCarroProducto(null);
		}
		
	
		pedidos.save(pedidoCliente);
		clientes.save(clienteService.getClienteActual());
		carros.save(clienteService.getClienteActual().getCarroCliente());
		
		String nombreCliente = clienteService.getClienteActual().getNombreCompleto();
        String correoCliente = clienteService.getClienteActual().getCorreo();
        String precioPedido = "" + costePedidoFormateado;
        String fechaLlegada = "00/00/0000";
        String datosCorreo = nombreCliente + "-" + correoCliente + "-"+ direccionCliente + "-" + precioPedido + "-" + fechaLlegada;

        ServicioInterno servicioInterno = new ServicioInterno();
        servicioInterno.sendMail(datosCorreo);
		
		return "realizar-pedido";
	}


}
