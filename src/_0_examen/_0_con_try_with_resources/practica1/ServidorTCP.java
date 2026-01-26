package _0_examen._0_con_try_with_resources.practica1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServidorTCP {

	private ServerSocket serverSocket;
	private int port;
	private static AtomicLong socketAceptados = new AtomicLong(0);

	public ServidorTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	public static AtomicLong getSocketAceptados() {
		return socketAceptados;
	}

	public static void main(String[] args) throws IOException {

		// creamos server socket con puerto
		ServidorTCP servidor = new ServidorTCP(8080);

		// conectamos con el cliente + creamos nuevo hilo para cada cliente con gestor
		// de socket server
		while (true) {
			Socket socket = null;

			try {
				System.out.printf("(S:%d) → Esperando conexión...%n", servidor.port);
				
				socket = servidor.serverSocket.accept();
				
				System.out.printf("(S:%d) → ¡Conexión establecida!%n", servidor.port);
				
				socketAceptados.getAndIncrement();
				
				new Thread(new GestorSocketServer(socket)).start();
				
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

		}

	}

}
