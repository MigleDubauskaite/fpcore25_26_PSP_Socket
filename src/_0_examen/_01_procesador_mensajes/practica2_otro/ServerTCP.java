package _0_examen._01_procesador_mensajes.practica2_otro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private int port;
	private ServerSocket serverSocket;
	private static AtomicLong clientesAceptados = new AtomicLong(0);

	public static AtomicLong getClientesAceptados() {
		return clientesAceptados;
	}

	public ServerTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	public static void main(String[] args) {

		ServerTCP servidor = null;
		Socket socket = null;

		try {
			servidor = new ServerTCP(8089);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (true) {
			try {
				System.out.printf("(Server) Estableciendo conexión en el puerto %d%n", servidor.port);
				socket = servidor.serverSocket.accept();
				System.out.printf("(Server) Cliente conectado en el puerto %d%n", servidor.port);
				clientesAceptados.incrementAndGet();
				new Thread(new GestorSocketServer(socket)).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
