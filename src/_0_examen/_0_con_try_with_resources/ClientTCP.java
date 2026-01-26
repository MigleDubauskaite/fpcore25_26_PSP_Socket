package _0_examen._0_con_try_with_resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// SIMULA UN JUGADOR AUTOMÁTICO
// SE CONECTA AL SERVIDOR + ENVÍA NÚMEROS + LEE RESPUESTAS + CUENTA INTENTOS
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
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

//			POR CADA NÚMERO: ENVÍA INTENTO, LEE RESPUESTA, INCREMENTA INTENTOS
			for (int i = 0; i < 1000; i++) {
				pw.println(i);
				intentos++;

				String respuesta = br.readLine();

//				SI ACIERTA, CIERRA LA CONEXIÓN
				if ("Acertado".equals(respuesta)) {
					System.out.printf("(Cliente) Dato acertado después de %d intentos%n", intentos);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			ClientTCP client = new ClientTCP("localhost", 8080);
			client.iniciarCliente();
		}
	}

}
