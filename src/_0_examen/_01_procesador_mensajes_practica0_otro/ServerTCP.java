package _0_examen._01_procesador_mensajes_practica0_otro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private ServerSocket serverSocket;
	private int port;
	private static AtomicLong peticionesRecibidas = new AtomicLong(0);

	public static AtomicLong getPeticionesRecibidas() {
		return peticionesRecibidas;
	}

	public ServerTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	public static void main(String[] args) throws IOException {

		ServerTCP servidor = new ServerTCP(8080);

		while (true) {
			System.out.printf("[S:%d] -> Esperando conexión...%n", servidor.port);

			Socket socket = servidor.serverSocket.accept();
			peticionesRecibidas.getAndIncrement();
			System.out.printf("[S:%d] -> Cliente conectado %n", servidor.port);
			new Thread(new GestorSocketServer(socket)).start();
		}
	}

}
