package _0_examen._0_con_try_with_resources.practica1;

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
	private JuegoAdivinaNumero juego;

	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		juego = new JuegoAdivinaNumero();
	}

	@Override
	public void run() {
		try (InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true);
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			// lee los intentos de los clientes

			String respuestaLeida = null;

			while ((respuestaLeida = br.readLine()) != null) {
				peticionesAlServidor.getAndIncrement();
				
				System.out.println("(GS) Cliente envió: " + respuestaLeida);

				String respuesta = juego.verificar(respuestaLeida);
				System.out.println("(GS) Servidor responde: " + respuesta);
				
				pw.println(respuesta);

				if ("Acertado".equals(respuesta)) {
					System.out.printf("(GS) -> %d sockets aceptados y %d peticiones aceptadas%n",
							ServidorTCP.getSocketAceptados().get(), peticionesAlServidor.get());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
