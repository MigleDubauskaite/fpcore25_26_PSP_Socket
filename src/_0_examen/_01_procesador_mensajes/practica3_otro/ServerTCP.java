package _0_examen._01_procesador_mensajes.practica3_otro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private ServerSocket socket;
	private int port;
	private static AtomicLong peticionesRecibidas = new AtomicLong(0);

	public ServerTCP(int port) throws IOException {
		this.port = port;
		socket = new ServerSocket(port);
	}

	public static AtomicLong getPeticionesRecibidas() {
		return peticionesRecibidas;
	}

	public static void main(String[] args) {

		ServerTCP server = null;
		Socket socket = null;

		try {
			server = new ServerTCP(8084);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				System.out.printf("Server: Estableciendo conexión con el cliente en el puerto %d%n", server.port);
				socket = server.socket.accept();
				System.out.printf("Server: Conexión establecida con el cliente en el puerto %d%n", server.port);
				peticionesRecibidas.incrementAndGet();
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
