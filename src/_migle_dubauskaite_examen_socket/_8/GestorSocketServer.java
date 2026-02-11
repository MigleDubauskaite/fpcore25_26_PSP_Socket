package _migle_dubauskaite_examen_socket._8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private final Socket socket;
	private static AtomicLong peticionesRecbidas = new AtomicLong(0);
	private GestorOperaciones gestor;

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		gestor = new GestorOperaciones();
	}

	@Override
	public void run() {
		try (socket;
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			String mensaje;

			while ((mensaje = br.readLine()) != null) {
				peticionesRecbidas.incrementAndGet();
				System.out.printf("[SERVIDOR] Petición recibida: %s%n", mensaje);

				String respuesta = gestor.verificarMensajes(mensaje);
				pw.println(respuesta);
				System.out.printf("[SERVIDOR] Respuesta enviada: %s%n", respuesta);

				if ("#Conexion cerrada#".equals(respuesta)) {
					System.out.println("Fin de conexión");
					break;
				}
			}
			
			System.out.printf("Estadisticas %nTotal clientes aceptados: %d | Total peticiones recibidas: %d%n", 
					ServerTCP.getClientesAceptados().get(), peticionesRecbidas.get());

		} catch (IOException e) {
			System.out.printf("[SERVIDOR] Error: %s%n", e.getMessage());
		}

	}

}
