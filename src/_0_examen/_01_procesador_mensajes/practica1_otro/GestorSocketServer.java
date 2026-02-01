package _0_examen._01_procesador_mensajes.practica1_otro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private Socket socket;
	private Calculadora calculadora;
	private static AtomicLong peticionesAceptadas = new AtomicLong(0);

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		calculadora = new Calculadora();
	}

	@Override
	public void run() {

		try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			String recibido;

			while ((recibido = br.readLine()) != null) {

				peticionesAceptadas.getAndIncrement();

				System.out.printf("(GS) Recibido: %s %n", recibido);

				String respuesta = calculadora.procesarMensaje(recibido);

				System.out.printf("(GS) Respuesta: %s %n", respuesta);

				pw.println(respuesta);

				if (respuesta.equals("#ADIOS#")) {
					System.out.printf("(GS) Sockets aceptados: %d, Peticiones aceptadas: %d %n",
							ServerTCP.getPeticionesRecibidas().get(), peticionesAceptadas.get());
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
