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
		System.out.printf("S -> Estableciendo conexión en el puerto %d%n", port);
	}

	public static AtomicLong getClientesAceptados() {
		return clientesAceptados;
	}

	public void iniciar() {
		while (true) {
			try {
				System.out.println("S -> Esperando conexión...");
				Socket socket = serverSocket.accept();
				long numCliente = clientesAceptados.incrementAndGet();
				System.out.printf("S:%d -> Cliente %d conectado%n", port, numCliente);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		try {
			ServerTCP servidor = new ServerTCP(8060);
			servidor.iniciar();
		} catch (IOException e) {
			System.err.println("S -> No se pudo iniciar el servidor");
			e.printStackTrace();
		}

	}

}
