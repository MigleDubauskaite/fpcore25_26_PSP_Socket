package _migle_dubauskaite_examen_socket._9;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private int port;
	private ServerSocket serverSocket;
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

		try {
			servidor = new ServerTCP(5555);
			System.out.printf("[Servidor] Estableciendo conexión en el puerto: %d%n", servidor.port);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (true) {
			try {
				Socket socket = servidor.serverSocket.accept();
				long numCliente = clientesAceptados.incrementAndGet();
				System.out.printf("Servidor -> Cliente %d conectado en puerto %d%n", numCliente, servidor.port);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				System.out.printf("[Servidor] Error al conectar cliente: %s%n", e.getMessage());
			}

		}

	}

}
