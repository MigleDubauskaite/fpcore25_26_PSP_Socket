package _0_examen._01_procesador_mensajes.mi_intento_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private Socket socket;
	private JuegoInstrucciones juego;
	private static AtomicLong peticionesAceptadas = new AtomicLong(0);

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		juego = new JuegoInstrucciones();
	}

	@Override
	public void run() {

		try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			String mensajeRecibido;

			while ((mensajeRecibido = br.readLine()) != null) {
				peticionesAceptadas.incrementAndGet();

				String respuesta = juego.procesarInstrucciones(mensajeRecibido);

				pw.println(respuesta);

				System.out.printf("(Gestor Server) Mensaje recibido: %s%n", mensajeRecibido);
				System.out.printf("(Gestor Server) Respuesta enviada: %s%n", respuesta);

				if ("#Finalizado#".equals(respuesta)) {
					System.out.printf("(Gestor Server) Cliente solicitó finalización.%n");
					return;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				System.out.printf("(Gestor Server) Total peticiones recibidas: %d. | Total clientes aceptados: %d %n",
						ServerTCP.getClientesAceptados().get(), peticionesAceptadas.get());
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
