package _migle_dubauskaite_examen_socket._1;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {
	
	private int port;
	private static AtomicLong clientesAceptados = new AtomicLong(0);
	public ServerTCP(int port) {
		this.port = port;
	}
	public static AtomicLong getClientesAceptados() {
		return clientesAceptados;
	}
	
	public void ejecutar() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.printf("S -> Estableciendo conexión en el puerto %d%n", port);
			while(true) {
				Socket socket = serverSocket.accept();
				clientesAceptados.incrementAndGet();
				System.out.printf("S -> Cliente conectado en el puerto %d%n", port);
				new Thread(new GestorSocketServer(socket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ServerTCP server = new ServerTCP(8070);
		server.ejecutar();
	}


}
