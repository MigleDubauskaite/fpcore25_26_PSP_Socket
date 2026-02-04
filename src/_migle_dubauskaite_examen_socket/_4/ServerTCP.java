package _migle_dubauskaite_examen_socket._4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

	private int port;
	private ServerSocket serverSocket;
	private static AtomicLong clientesAceptados = new AtomicLong(0);

	public ServerTCP(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		this.port = port;
		System.out.printf("S -> Estableciendo conexión en el puerto %d%n", port);
	}

	public static AtomicLong getClientesAceptados() {
		return clientesAceptados;
	}

	public void iniciar() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				long clienteNum = clientesAceptados.incrementAndGet();
				System.out.printf("S:%d -> Cliente %d aceptado%n", port, clienteNum);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				System.out.printf("S -> Error: %s%n", e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			ServerTCP servidor = new ServerTCP(8085);
			servidor.iniciar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
