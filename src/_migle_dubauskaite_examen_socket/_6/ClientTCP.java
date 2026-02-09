package _migle_dubauskaite_examen_socket._6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

	private String IP;
	private int port;
	private static long MAX_INTENTOS = 5;

	public ClientTCP(String iP, int port) {
		IP = iP;
		this.port = port;
	}

	private void iniciarCliente() {

		try (Socket socket = new Socket(IP, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				Scanner sc = new Scanner(System.in)) {

			int intento = 0;

			while (intento < MAX_INTENTOS) {
				intento++;
				System.out.printf("Cliente (%d/%d) -> ", intento, MAX_INTENTOS);
				String mensaje = sc.nextLine();
				pw.println(mensaje);

				String respuesta = br.readLine();
				System.out.printf("Cliente -> Respuesta: %s%n", respuesta);
				if (respuesta.equals("#ConexionCerrada#"))
					break;

			}

			System.out.printf("Cliente -> Conexión finalizada: %n");

		} catch (IOException e) {
			System.out.printf("Client -> Error: %s%n", e.getMessage());
		}

	}

	public static void main(String[] args) {
		new ClientTCP("localhost", 4045).iniciarCliente();
	}

}
