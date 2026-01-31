package _0_examen._01_procesador_mensajes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ClientTCP {

	private String serverIP;
	private int serverPort;
	private static AtomicLong intentos = new AtomicLong(0);

	public ClientTCP(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public void iniciarCliente() {
		
		try (Socket socket = new Socket(serverIP, serverPort);
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			
			for (int i = 0; i < 100; i++) {
				
			}
			
		} catch (Exception e) {
		}

	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			ClientTCP client = new ClientTCP("localhost", 8081);
			client.iniciarCliente();
		}
	}

}
