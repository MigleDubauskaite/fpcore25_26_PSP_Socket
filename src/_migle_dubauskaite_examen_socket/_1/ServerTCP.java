package _migle_dubauskaite_examen_socket._1;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {
	
	private int port;
	private static AtomicLong peticionesRecibidas = new AtomicLong(0);
	
	public ServerTCP(int port) {
		this.port = port;
	}

	public static AtomicLong getPeticionesRecibidas() {
		return peticionesRecibidas;
	}
	
	public void ejecutar() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			 System.out.printf("--- SERVIDOR INICIADO (Puerto: %d) ---%n", port);
			
			while(true) {
				Socket socket = serverSocket.accept();
				peticionesRecibidas.incrementAndGet();
				System.out.printf("--- CLIENTE CONECTADO (Puerto: %d) ---%n", port);
				//hilo
			}
			
		} catch (Exception e) {
			System.out.printf("--- ERROR EN EL SERVER (Puerto: %d): %s ---%n", port, e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		ServerTCP server = new ServerTCP(8090);
		server.ejecutar();
	}

}
