package _migle_dubauskaite_examen_socket._2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private ServerSocket serverSocket;
	private int port;
	private static AtomicLong clientesAceptados = new AtomicLong(0);

	public ServerTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	public static AtomicLong getClientesAceptados() {
		return clientesAceptados;
	}

	public static void main(String[] args) {

		ServerTCP servidor = null;
		Socket socket = null;

		try {
			servidor = new ServerTCP(8060);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			try {
				System.out.printf("S -> Estableciendo conexión en el puerto %d%n", servidor.port);
				socket = servidor.serverSocket.accept();
				clientesAceptados.incrementAndGet();
				System.out.printf("S -> Conexión establecida en el puerto %d%n", servidor.port);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
