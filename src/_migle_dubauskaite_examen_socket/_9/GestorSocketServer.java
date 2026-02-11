package _migle_dubauskaite_examen_socket._9;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private final Socket socket;
	private static AtomicLong peticionesRecibidas = new AtomicLong(0);
	private GestorMensajes gestor;

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		gestor = new GestorMensajes();
	}

	@Override
	public void run() {

		try (socket;
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			String mensaje;

			while ((mensaje = br.readLine()) != null) {
				peticionesRecibidas.incrementAndGet();
				System.out.printf("Servidor -> Mensaje: %s%n", mensaje);

				String respuesta = gestor.verificarMensaje(mensaje);
				pw.println(respuesta);
				System.out.printf("Servidor -> Respuesta: %s%n", respuesta);

				if ("#Conexion cerrada#".equals(respuesta)) {
					System.out.printf("Conexión cerrada%n");
					break;
				}
			}

			System.out.printf("Total clientes: %d ||| Total peticiones recibidas: %d%n",
					ServerTCP.getClientesAceptados().get(), peticionesRecibidas.get());

		} catch (Exception e) {
			System.out.printf("Servidor -> Error: %s%n", e.getMessage());
		}

	}

}
