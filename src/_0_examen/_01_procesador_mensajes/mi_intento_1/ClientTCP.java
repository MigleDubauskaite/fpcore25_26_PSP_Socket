package _0_examen._01_procesador_mensajes.mi_intento_1;

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
	private static AtomicLong nextID = new AtomicLong(1);

	private static AtomicLong intentosTotal = new AtomicLong(0);
	private static int MAX_INTENTOS = 20;

	private static final String[] MENSAJES = { "#Listado números#2#11#", "#Número aleatorio#3#20#", "#Fin#", "Adios" };

	public ClientTCP(String host, int port) {
		id = nextID.getAndIncrement();
		this.host = host;
		this.port = port;
	}

	public void iniciarCliente() {

		try (Socket socket = new Socket(host, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			Random random = new Random();

			for (int i = 0; i < MAX_INTENTOS; i++) {
				intentosTotal.incrementAndGet();

				int index = random.nextInt(MENSAJES.length);
				String mensaje = MENSAJES[index];

				pw.println(mensaje);
				System.out.printf("(Client %d) Mensaje enviado %s%n", id, mensaje);

				String respuesta = br.readLine();
				System.out.printf("(Client %d) Respuesta recibida %s%n", id, respuesta);

				if ("#Error#".equals(respuesta)) {
					System.out.println("Mensaje no adecuadamente formateado para su tratamiento.");
				}

				if ("#Finalizado#".equals(respuesta)) {
					System.out.printf("(Client %d) Juego terminado con total de %d intentos %n", id,
							intentosTotal.get());
					return;
				}
			}

			System.out.printf("(Client %d) Fin de intentos. Total intentos: %d%n", id, intentosTotal.get());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				ClientTCP cliente = new ClientTCP("localhost", 8085);
				cliente.iniciarCliente();
			}).start();

		}

	}

}
