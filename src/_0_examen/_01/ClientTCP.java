package _0_examen._01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP {

	private String serverIP;
	private int serverPort;
	private int intentos;

	public ClientTCP(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public void iniciarCliente() {

		try (Socket socket = new Socket(serverIP, serverPort);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			for (int i = 0; i < 20; i++) {
				pw.println();
				intentos++;

				String respuesta = br.readLine();
				System.out.println("(Cliente) Mensaje recibido: " + respuesta);

				if ("#Listado números#2#11#".equals(respuesta)) {
					System.out.printf("(Cliente) ha acertado el mensaje (de %d intentos) %n", intentos);
					break;
				}

			}

		} catch (Exception e) {
			e.getStackTrace();
		}

	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			ClientTCP client = new ClientTCP("localhost", 8081);
			client.iniciarCliente();
		}
	}

}
