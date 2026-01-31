package _0_examen._01_procesador_mensajes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private Socket socket;
	private static AtomicLong peticionesAlServidor = new AtomicLong(0);
	private Juego juego;

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		this.juego = new Juego();
	}

	@Override
	public void run() {

		try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			String datoRecibido;

			while ((datoRecibido = br.readLine()) != null) {

				peticionesAlServidor.incrementAndGet();
				String respuesta = juego.verificarMensajesValidos(datoRecibido);

				System.out.println("(Gestor socket) Petición recibida: " + datoRecibido);
				System.out.println("(Gestor socket) Respuesta enviada: " + respuesta);

				pw.println(respuesta);

				// CORTAR COMUNICACIÓN SEGÚN ENUNCIADO
				if ("#Finalizado#".equals(respuesta)) {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.printf("(Gestor socket) Conexión cerrada. Sockets aceptados: %d, Peticiones: %d%n",
					ServidorTCP.getSocketAceptados(), peticionesAlServidor.get());
		}
	}
}
