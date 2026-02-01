package _0_examen._01_procesador_mensajes.practica1_otro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;
import java.util.random.RandomGenerator;

public class ClientTCP {

	private String IP;
	private int port;
	private static RandomGenerator random = RandomGenerator.getDefault();
	private static AtomicLong intentos = new AtomicLong(0);

	public ClientTCP(String iP, int port) {
		IP = iP;
		this.port = port;
	}

	public void iniciarCliente() {

		try (Socket socket = new Socket(IP, port);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			for (int i = 0; i < 100; i++) {

				String mensajes[] = { "#SUMA#3#5#", "#RESTA#8#3#", "#MULT#1#2#", "#DIV#10#5#", "#FIN#", "Hola" };
				int index = random.nextInt(mensajes.length);
				String mensaje = mensajes[index];

				pw.println(mensaje);
				intentos.getAndIncrement();
				System.out.printf("(C) Mensaje enviado: %s %n", mensaje);

				String respuesta = br.readLine();
				System.out.printf("(C) Mensaje recibido: %s %n", respuesta);

				if (respuesta.equals("#ADIOS#")) {
					System.out.printf("(C) Dato aceptado después de %d intentos%n", intentos.get());
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			ClientTCP cliente = new ClientTCP("localhost", 9090);
			cliente.iniciarCliente();
		}
	}

}
