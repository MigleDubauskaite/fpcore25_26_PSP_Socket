package _migle_dubauskaite_examen_socket._6;

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
		System.out.printf("Server -> Intentando establecer conexión en el puerto %d%n", port);
	}

	public static void main(String[] args) {
		ServerTCP servidor = null;
		Socket socket = null;

		try {
			servidor = new ServerTCP(4045);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				socket = servidor.serverSocket.accept();
				long numCliente = clientesAceptados.incrementAndGet();
				System.out.printf("Server -> Cliente %d conectado %n", numCliente);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				System.out.printf("(Server: %d) Error: %s%n", servidor.port, e.getMessage());
			}
		}

	}

}
