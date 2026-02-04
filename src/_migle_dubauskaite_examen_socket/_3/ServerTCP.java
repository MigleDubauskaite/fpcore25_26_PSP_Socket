package _migle_dubauskaite_examen_socket._3;

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
		System.out.printf("S → Esperando conexión en puerto %d%n", port);
	}

	public static AtomicLong getClientesAceptados() {
		return clientesAceptados;
	}
	
	public void iniciar() {
		
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				long numCliente = clientesAceptados.incrementAndGet();
				System.out.printf("S → Cliente %d conectado %d%n", numCliente, port);
				new Thread(new GestorSocketServer(socket)).start();
			} catch (IOException e) {
				System.out.printf("S → Error: %s%n", e.getMessage());
			}
		}
		
	}
	
	public static void main(String[] args) {
		try {
			ServerTCP servidor = new ServerTCP(8080);
			servidor.iniciar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
