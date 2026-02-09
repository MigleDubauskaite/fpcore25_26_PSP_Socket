package _migle_dubauskaite_examen_socket._5;

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
		System.out.printf("Server -> Estableciendo conexión: %d%n", port);
	}

	public void iniciar() {

		while (true) {
			try {
				Socket socket = serverSocket.accept();
				long numCliente = clientesAceptados.incrementAndGet();
				System.out.printf("Server %d -> Cliente %d aceptado%n", port, numCliente);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				System.out.printf("Server -> Error: %s%n", e.getMessage());
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		try {
			ServerTCP servidor = new ServerTCP(8081);
			servidor.iniciar();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
