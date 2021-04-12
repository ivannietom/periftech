package serviciointerno;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServicioInterno {

    public static void main(String[] args) throws ClassNotFoundException {
        ServerSocket server;
        Socket socket;
        int puerto = 9000;
        BufferedReader entrada;
         
        try{
            server = new ServerSocket(puerto);
            socket = new Socket();
            while((socket = server.accept()) != null) {
	            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            String mensaje = entrada.readLine();
	            String partes[] = mensaje.split("-");

	            String nombreCliente = partes[0];
	            String correoCliente = partes[1];
	            String direccionCliente = partes[2];
	            String precioPedido = partes[3];
	            String fechaLlegada = partes[4];
	             
	            String infoPedido = "Nombre cliente: " + nombreCliente +"\n"
	            		+ "Correo cliente: " + correoCliente + "\n"
	            		+ "Dirección a la que será enviado: " + direccionCliente + "\n"
	            		+ "Precio del pedido: " + precioPedido + "\n"
	            		+ "Fecha prevista de llegada: " + fechaLlegada;
	             
	             
	            System.out.println(infoPedido);
            }
        } catch(Exception e) {};
    }
}