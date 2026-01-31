package _0_examen._01_procesador_mensajes.mi_intento_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private ServerSocket socket;
	private int port;
	private static AtomicLong clientesAceptados = new AtomicLong(0);

	public static AtomicLong getClientesAceptados() {
		return clientesAceptados;
	}

	public ServerTCP(int port) throws IOException {
		this.port = port;
		socket = new ServerSocket(port);
	}

	public static void main(String[] args) {

		ServerTCP server = null;
		Socket socket = null;

		try {
			server = new ServerTCP(8085);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		while (true) {

			try {
				System.out.printf("(Server) Esperando conexión en el puerto%d.%n", server.port);
				socket = server.socket.accept();
				System.out.printf("(Server) Cliente aceptado en el puerto %d.%n", server.port);
				clientesAceptados.incrementAndGet();
				new Thread(new GestorSocketServer(socket)).start();
			}

			catch (IOException e) {
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

}
