package _migle_dubauskaite_examen_socket._2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private static AtomicLong peticionesRecibidas = new AtomicLong(0);
	private final Socket socket;
	private ProtocoloComunicacion protocolo;

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		protocolo = new ProtocoloComunicacion();
	}

	@Override
	public void run() {
		try (socket;
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			String mensaje;

			while ((mensaje = br.readLine()) != null) {
				peticionesRecibidas.incrementAndGet();
				System.out.printf("Gestor Server -> Mensaje recibido: %s%n", mensaje);

				// verificar, pw
				String respuesta = protocolo.procesar(mensaje);
				System.out.printf("Gestor Server -> Respuesta enviada: %s%n", respuesta);
				pw.println(respuesta);

				if ("#FIN#".equals(mensaje)) break;

			}

			System.out.printf("Gestor Server -> Conexión cerrada.%n");

		} catch (Exception e) {
			System.out.printf("Gestor Server -> Error %s.%n", e.getMessage());
			return;
		}

	}

}
