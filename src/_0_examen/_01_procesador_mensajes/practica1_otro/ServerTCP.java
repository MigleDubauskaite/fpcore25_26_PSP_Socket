package _0_examen._01_procesador_mensajes.practica1_otro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private ServerSocket server;
	private int port;
	private static AtomicLong peticionesRecibidas = new AtomicLong(0);

	public ServerTCP(int port) throws IOException {
		this.port = port;
		server = new ServerSocket(port);
	}

	public static AtomicLong getPeticionesRecibidas() {
		return peticionesRecibidas;
	}

	public static void main(String[] args) throws IOException {

		ServerTCP servidor = new ServerTCP(9090);

		while (true) {

			System.out.printf("(S:%d) Esperando conexión...%n", servidor.port);
			Socket socket = servidor.server.accept();
			peticionesRecibidas.getAndIncrement();
			System.out.printf("(S:%d) Cliente conectado%n", servidor.port);

			new Thread(new GestorSocketServer(socket)).start();

		}
	}
}
