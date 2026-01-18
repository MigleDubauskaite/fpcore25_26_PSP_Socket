package _0_examen._000Correcto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

// CEREBRO DEL SERVIDOR PARA CADA CLIENTE (MANEJA CLIENTE)
// CADA VEZ QUE UN CLIENTE SE CONECTA:
// SE CREA UN OBJETO DE ESTA CLASE + SE EJECUTA UN HILO INDEPENDIENTE, GESTIONA TODA COMUNICACIÓN CON ESTE CLIENTE
public class GestorSocketServer implements Runnable {

	private JuegoAdivinaNumero juego;
	private Socket socket;
	private static AtomicLong peticionesAlServidor = new AtomicLong(0);

	public GestorSocketServer(Socket socket) {  // RECIBE SOCKET DEL CLIENTE
		this.socket = socket;
		juego = new JuegoAdivinaNumero(); // CREA UN JUEGO NUEVO PARA ESE CLIENTE
	}

	@Override
	public void run() {
		try (InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true);
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			String datoLeido;
			while ((datoLeido = br.readLine()) != null) {
				peticionesAlServidor.getAndIncrement();
				String respuesta = juego.verificarIntento(datoLeido); // COMPRUEBA SI EL NÚMERO ES CORRECTO
				pw.println(respuesta); // ENVÍA LA RESPUESTA AL CLIENTE

				if ("Acertado".equals(respuesta)) {
					System.out.printf("(Gestor sockets) %d sockets aceptados y %d peticiones recibidas%n",
							ServidorTCP.getSocketAceptados(), peticionesAlServidor.get());
					break;
				}
			}
		} catch (IOException e) {
			System.err.println("(Gestor sockets) Error en la comunicación con el cliente: " + e.getMessage());
		}
	}
}
