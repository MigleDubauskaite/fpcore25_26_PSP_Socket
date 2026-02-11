package _migle_dubauskaite_examen_socket._8;

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
		try {
			servidor = new ServerTCP(7777);
			System.out.printf("[SERVIDOR] Estableciendo conexión en puerto %d%n", servidor.port);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (true) {
			try {
				Socket socket = servidor.serverSocket.accept();
				long numCliente = clientesAceptados.incrementAndGet();
				System.out.printf("[SERVIDOR] Cliente %d conectado desde: %s:%d%n", numCliente, servidor.port);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				System.out.printf("[SERVIDOR] Error: %s%n", e.getMessage());
				break;
			}
		}

	}

}
