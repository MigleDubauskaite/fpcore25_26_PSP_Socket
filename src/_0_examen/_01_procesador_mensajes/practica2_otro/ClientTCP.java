package _0_examen._01_procesador_mensajes.practica2_otro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClientTCP {

	private String host;
	private int port;
	private long id;
	private static AtomicLong ID = new AtomicLong(1);
	private static AtomicLong intentos = new AtomicLong(0);

	private static final String[] mensajes = { "REPETIR|hola|3|_", "CONTAR|banana|a|_", "MAYUSCULAS|hola mundo|_|_", "SALIR|_|_|_" };

	private static int MAX_INTENTOS = 15;

	public ClientTCP(String host, int port) {
		id = ID.getAndIncrement();
		this.host = host;
		this.port = port;
	}

	public void iniciarCliente() {

		Random random = new Random();

		try (Socket socket = new Socket(host, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			for (int i = 0; i < MAX_INTENTOS; i++) {

				intentos.getAndIncrement();

				int index = random.nextInt(mensajes.length);
				String mensaje = mensajes[index];
				pw.println(mensaje);
				System.out.printf("(Client %d) Mensaje enviado: %s%n", id, mensaje);

				String respuesta = br.readLine();
				System.out.printf("(Client %d) Respuesta recibida: %s%n", id, respuesta);

				if ("FIN".equals(respuesta)) {
					System.out.printf("(Client %d) Cerrando la conexión después de %d intentos%n", id,
							intentos.get());
					return;
				}
			}
			System.out.printf("(Client %d) Cerrando la conexión después de %d intentos%n", id, intentos.get());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				ClientTCP client = new ClientTCP("localhost", 8089);
				client.iniciarCliente();
			}).start();;
		}
	}

}
