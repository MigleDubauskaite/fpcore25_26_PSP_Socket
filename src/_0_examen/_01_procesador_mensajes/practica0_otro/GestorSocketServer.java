package _0_examen._01_procesador_mensajes.practica0_otro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private Socket socket;
	private ProcesadorMensajes procesador;
	private static AtomicLong peticionesAlServidor = new AtomicLong(0);

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		procesador = new ProcesadorMensajes();
	}

	@Override
	public void run() {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {

			String intentoLeido;

			while ((intentoLeido = br.readLine()) != null) {
				peticionesAlServidor.getAndIncrement();

				System.out.printf("[GS] -> Recibido: %s %n", intentoLeido);

				String respuesta = procesador.procesarMensajes(intentoLeido);
				System.out.printf("[GS] -> Respuesta: %s %n", respuesta);

				pw.println(respuesta);

				if (respuesta.equals("#Finalizado#")) {
					System.out.printf("[GS] -> %d sockets aceptados y %d peticiones aceptadas %n",
							ServerTCP.getPeticionesRecibidas().get(), peticionesAlServidor.get());
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
