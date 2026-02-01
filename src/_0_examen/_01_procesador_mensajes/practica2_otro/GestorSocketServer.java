package _0_examen._01_procesador_mensajes.practica2_otro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {
	
	private Socket socket;
	private GestorComandos gestor;
	private static AtomicLong peticionesRecibidas = new AtomicLong(0);
	
	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		gestor = new GestorComandos();
	}

	@Override
	public void run() {
		
		try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			
			String mensajeRecibido;
			
			while((mensajeRecibido = br.readLine())!= null) {
				
				peticionesRecibidas.getAndIncrement();
				
				System.out.printf("(GS) Mensaje recibido: %s %n", mensajeRecibido);
				
				String respuesta = gestor.gestionarComandos(mensajeRecibido);
				
				System.out.printf("(GS) Respuesta enviada: %s %n", respuesta);
				pw.println(respuesta);
				
				if ("FIN".equals(respuesta)) {
					System.out.printf("(GS) Finalizando la comunicación con el cliente%n");
					return;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.printf("(GS) Total peticiones recibidas %d. Total clientes conectados: %d %n", peticionesRecibidas.get(),
						ServerTCP.getClientesAceptados().get());
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	

}
