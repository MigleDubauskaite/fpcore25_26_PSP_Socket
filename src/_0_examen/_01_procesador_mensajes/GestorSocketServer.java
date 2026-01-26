package _0_examen._01_procesador_mensajes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private Socket socket;
	private static AtomicLong peticionesAlServidor = new AtomicLong(0);
	private Juego juego;

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		juego = new Juego();
	}

	@Override
	public void run() {

		try (InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));) {

			String datoRecibido;

			while ((datoRecibido = br.readLine()) != null) {
				peticionesAlServidor.getAndIncrement();
				String respuesta = juego.verificarMensajesValidos(datoRecibido);
				System.out.println("(Gestor socket) Petición recibida: " + datoRecibido);
				pw.println(respuesta);
				System.out.println("(Gestor socket) Respuesta enviada: " + datoRecibido);

				// se cierra la conexión si el mensaje recibido es #Fin#
				if ("#Fin#".equals(respuesta)) {
					System.out.printf("(Gestor socket) %d sockets aceptados y %d peticiones recibidas %n",
							ServidorTCP.getSocketAceptados(), peticionesAlServidor.get());
					break;
				}
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

	}

}
