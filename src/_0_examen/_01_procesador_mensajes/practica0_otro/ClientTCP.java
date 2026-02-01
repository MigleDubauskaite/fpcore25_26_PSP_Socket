package _0_examen._01_procesador_mensajes.practica0_otro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;
import java.util.random.RandomGenerator;

public class ClientTCP {

	private String IP;
	private int port;
	private static AtomicLong intentos = new AtomicLong(0);
	private static RandomGenerator random = RandomGenerator.getDefault();

	public ClientTCP(String IP, int port) {
		this.IP = IP;
		this.port = port;
	}

	public void iniciarCliente() {

		try (Socket socket = new Socket(IP, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			for (int i = 0; i < 100; i++) {

				String mensajes[] = { "#Saludo#", "#Eco#Hola mundo#", "Hola", "#Fin#" };
				int mensajeIndex = random.nextInt(mensajes.length);
				String mensaje = mensajes[mensajeIndex];

				pw.println(mensaje);
				intentos.getAndIncrement();
				System.out.println("(C) → Enviado: " + mensaje);

				String respuesta = br.readLine();
				System.out.println("(C) Servidor respondió: " + respuesta);

				if (respuesta.equals("#Finalizado#")) {
					System.out.printf("(C) -> Dato acertado después de %d intentos %n", intentos.get());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		for (int i = 0; i < 3; i++) {
			ClientTCP cliente = new ClientTCP("localhost", 8080);
			cliente.iniciarCliente();
		}

	}

}
