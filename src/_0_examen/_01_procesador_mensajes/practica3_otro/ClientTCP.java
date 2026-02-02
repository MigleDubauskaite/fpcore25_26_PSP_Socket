package _0_examen._01_procesador_mensajes.practica3_otro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClientTCP {

	private String host;
	private int port;
	private static AtomicLong intentos = new AtomicLong(0);
	private long id;
	private static AtomicLong nextID = new AtomicLong(1);

	//private static final String[] MENSAJES = { "#RANGO#3#7#", "#ALEATORIO#10#20#", "#CONTAR#banana#a#", "#FIN#",
			//"RANGO#3#2" };
	private static final String[] MENSAJES = {"#RANGO#3#7#"};

	public ClientTCP(String host, int port) {
		this.host = host;
		this.port = port;
		id = nextID.getAndIncrement();
	}

	public void iniciarCliente() {
		try (Socket socket = new Socket(host, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			
			Random random = new Random();
			for (int i = 0; i < 20; i++) {
				intentos.incrementAndGet();
				
				int index = random.nextInt(MENSAJES.length);
				String mensaje = MENSAJES[index];
				pw.println(mensaje);
				System.out.printf("Client %d: Mensaje enviado: %s%n", id, mensaje);
				
				String respuesta = br.readLine();
				System.out.printf("Client %d: Respuesta recibida: %s%n", id, respuesta);
				
				if ("#OK#".equals(respuesta)) {
					System.out.printf("Client %s:%d: Cerramos la conexión después de %d intentos.%n", host, port, intentos.get());
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(()->{
				ClientTCP client = new ClientTCP("localhost", 8084);
				client.iniciarCliente();
			}).start();
		}
	}

}
