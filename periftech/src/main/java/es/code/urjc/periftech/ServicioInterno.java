package es.code.urjc.periftech;

import java.io.PrintStream;
import java.net.Socket;

public class ServicioInterno {

	public ServicioInterno() {}

	public void send(String mensaje) {
		Socket cliente;
		int puerto = 9000;
		String ip = "127.0.0.1";
		PrintStream salida;

		try {
			cliente = new Socket(ip, puerto);
			salida = new PrintStream(cliente.getOutputStream());
			salida.println(mensaje);

			salida.close();
			cliente.close();
		} catch (Exception e) {}
	}
}
