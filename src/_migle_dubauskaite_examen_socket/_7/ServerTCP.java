package _migle_dubauskaite_examen_socket._7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private int port;
	private static AtomicLong clientesAceptados = new AtomicLong(0);
	private ServerSocket serverSocket;

	public static AtomicLong getClientesAceptados() {
		return clientesAceptados;
	}

	public ServerTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
		System.out.printf("Server -> Estableciendo conexión en puerto %d%n", port);
	}

	public static void main(String[] args) {

		ServerTCP server = null;
		Socket socket = null;

		try {
			server = new ServerTCP(8888);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				socket = server.serverSocket.accept();
				long clienteNum = clientesAceptados.incrementAndGet();
				System.out.printf("Server -> Cliente %d conectado %n", clienteNum);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				System.out.printf("Server: %d -> Error: %s%n", server.port, e.getMessage());
			}
		}

	}

}
