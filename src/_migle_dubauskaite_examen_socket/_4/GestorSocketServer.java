package _migle_dubauskaite_examen_socket._4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private final Socket socket;
	private static AtomicLong peticionesRecibidas = new AtomicLong(0);
	private Protocolo protocolo;

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		protocolo = new Protocolo();
	}

	@Override
	public void run() {

		try (socket;
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			String mensaje;

			while ((mensaje = br.readLine()) != null) {
				peticionesRecibidas.incrementAndGet();
				System.out.printf("Gestor -> Mensaje: %s%n", mensaje);
				String respuesta = protocolo.verificar(mensaje);
				pw.println(respuesta);
				System.out.printf("Gestor -> Respuesta: %s%n", respuesta);
				if ("#ConexionCerrada#".equals(respuesta))
					break;
			}

			System.out.printf("Gestor -> Comunicación finalizada.%n");

		} catch (IOException e) {
			System.out.printf("Gestor -> %s%n", e.getMessage());
		} finally {
			System.out.printf("Estadisticas: %n 1) Total clientes aceptados: %d%n 2) Total peticiones recibidas: %d%n",
					ServerTCP.getClientesAceptados().get(), peticionesRecibidas.get());
		}

	}

}
