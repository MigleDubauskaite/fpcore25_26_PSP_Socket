package _migle_dubauskaite_examen_socket._1;

import java.io.BufferedReader;
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

	public void iniciarCliente() {

		try (Socket socket = new Socket(host, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				Scanner sc = new Scanner(System.in)) {

			System.out.println("TOTAL INTENTOS: " + MAX_INTENTOS);
			int intentos = 0;

			while (intentos < MAX_INTENTOS) {
				
				intentos++;

				System.out.printf("Cliente(%d/%d) > ", intentos, MAX_INTENTOS);

				String mensaje = sc.nextLine();
				System.out.printf("C -> Mensaje enviado: %s%n", mensaje);
				pw.println(mensaje);

				String respuesta = br.readLine();
				System.out.printf("%nC -> Respuesta recibida: %s%n", respuesta);

			}
			
			System.out.printf("%n[!] Límite alcanzado. Enviando cierre...%n");
            pw.println("#FIN#"); 

		} catch (Exception e) {
			System.out.printf("C -> Error en la conexión %s%n", e.getMessage());
		}

	}

	public static void main(String[] args) {
		new ClientTCP("localhost", 8070).iniciarCliente();
	}

}
