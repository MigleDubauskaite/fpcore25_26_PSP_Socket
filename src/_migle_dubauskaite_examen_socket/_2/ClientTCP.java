package _migle_dubauskaite_examen_socket._2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
	
	private String host;
	private int port;
	private static int MAX_INTENTOS = 4;
	
	public ClientTCP(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void iniciarCliente() {
		try (Socket socket = new Socket(host, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				Scanner sc = new Scanner(System.in)) {
			
			int intentos = 0;
			
			while(intentos < MAX_INTENTOS) {
				intentos++;
				
				System.out.printf("C -> Mensajes posibles de comunicación: #RANDOM3#min#max# | #SUMA#n1,n2,n3,...# | #VOCALES#texto# | #MAYUS#texto# | #FIN#%n");
				System.out.printf("Cliente (%d/%d) > ", intentos, MAX_INTENTOS);
				
				String mensaje = sc.nextLine();
				pw.println(mensaje);
				System.out.printf("%nC -> Mensaje enviado: %s%n", mensaje);
				
				String respuesta = br.readLine();
				System.out.printf("%nC -> Respuesta recibida: %s%n", respuesta);
				
				if("#ConexionCerrada#".equals(respuesta)) break;
			}
			
			System.out.printf("C -> Conexión cerrada.%n");
						
		} catch (Exception e) {
			System.out.printf("Client  -> Error %s.%n", e.getMessage());
			return;
		}
	}
	
	public static void main(String[] args) {
		new ClientTCP("localhost", 8060).iniciarCliente();
	}
	

}
