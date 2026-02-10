package _migle_dubauskaite_examen_socket._7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

	private String host;
	private int port;

	private static int MAX_INTENTOS = 5;

	public ClientTCP(String host, int port) {
		this.host = host;
		this.port = port;
	}

	private void iniciarCliente() {
		try (Socket socket = new Socket(host, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				Scanner sc = new Scanner(System.in)) {

			int intentos = 0;

			while (intentos < MAX_INTENTOS) {
				intentos++;
				System.out.printf("Cliente [%d/%d] >>> ", intentos, MAX_INTENTOS);
				String mensaje = sc.nextLine();
				pw.println(mensaje);

				String respuesta = br.readLine();
				System.out.printf("Cliente -> Respuesta: %s%n", respuesta);

				if ("#Finalizado#".equals(respuesta))
					break;
				
				if ("#Error#".equals(respuesta)) {
				    System.out.println("Mensaje no adecuadamente formateado para su tratamiento.");
				}
			}

		} catch (IOException e) {
			System.out.printf("Cliente -> Error: %s%n", e.getMessage());
		}
	}

	public static void main(String[] args) {
		new ClientTCP("localhost", 8888).iniciarCliente();
	}

}
