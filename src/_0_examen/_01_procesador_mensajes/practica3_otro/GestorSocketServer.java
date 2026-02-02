package _0_examen._01_procesador_mensajes.practica3_otro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {
	
	private Socket socket;
	private static AtomicLong peticionesAceptadas = new AtomicLong(0);
	private InterpreteInstrucciones interpreteInstrucciones;
	
	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		interpreteInstrucciones = new InterpreteInstrucciones();
	}

	@Override
	public void run() {
		
		try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), true); 
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())) ) {
			
			String mensaje;
			
			while((mensaje = br.readLine())!=null) {
				
				peticionesAceptadas.incrementAndGet();
				System.out.printf("Gestor: Mensaje recibido: %s%n", mensaje);
				
				// verificar mensaje y poner pw.println
				String respuesta = interpreteInstrucciones.verificarMensajes(mensaje);
				pw.println(respuesta);
				System.out.printf("Gestor: Respuesta enviada: %s%n", respuesta);
				
				if ("#OK#".equals(respuesta)) {
					System.out.printf("Gestor: Cerrando la conexión.%n");
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
