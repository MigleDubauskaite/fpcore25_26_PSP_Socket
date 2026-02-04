package _migle_dubauskaite_examen_socket._4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

	private String host;
	private int port;
	private static final int MAX_INTENTOS = 5;

	public ClientTCP(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void iniciarCliente() {

		try (Socket socket = new Socket(host, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				Scanner sc = new Scanner(System.in)) {

			System.out.println("Cliente aceptado");
			int intento = 0;

			while (intento < MAX_INTENTOS) {
				intento++;
				System.out.printf("Cliente (%d/%d) > ", intento, MAX_INTENTOS);
				String mensaje = sc.nextLine();
				pw.println(mensaje);
				System.out.printf("%nC -> Mensaje: %s%n", mensaje);

				String respuesta = br.readLine();
				System.out.printf("C -> Respuesta: %s%n", respuesta);

				if ("#ConexionCerrada#".equals(respuesta))
					break;

			}
			System.out.printf("C -> Comunicación finalizada.%n");

		} catch (IOException e) {
			System.out.printf("C -> Error: %s.%n", e.getMessage());
		}

	}

	public static void main(String[] args) {
		new ClientTCP("localhost", 8085).iniciarCliente();
	}

}
