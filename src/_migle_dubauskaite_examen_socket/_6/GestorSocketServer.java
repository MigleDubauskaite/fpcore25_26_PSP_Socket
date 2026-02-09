package _migle_dubauskaite_examen_socket._6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private static AtomicLong peticionesRecibidas = new AtomicLong(0);
	private final Socket socket;
	private ProtocoloTexto protocolo;

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		protocolo = new ProtocoloTexto();
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
				System.out.printf("Gestor -> Respuesta: %s%n", respuesta);
				pw.println(respuesta);

				if (respuesta.equals("#ConexionCerrada#"))
					break;
			}

			System.out.println("Gestor -> Conexión finalizada");
			System.out.printf("%nESTADISTICAS: %nTotal clientes aceptados: %d | Total peticiones recibidas: %d%n",
					ServerTCP.getClientesAceptados().get(), peticionesRecibidas.get());

		} catch (Exception e) {
			System.out.printf("Gestor -> Error: %s%n", e.getMessage());
		}

	}

}
