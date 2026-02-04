package _migle_dubauskaite_examen_socket._1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class GestorSocketServer implements Runnable {

	private final Socket socket;
	private static AtomicLong peticionesProcesadas = new AtomicLong(0);
	private ProtocoloMensajes protocolo;
	
	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		protocolo = new ProtocoloMensajes();
	}

	@Override
	public void run() {
		try (socket;
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			
			String mensaje;
			
			while((mensaje = br.readLine()) != null) {
				peticionesProcesadas.incrementAndGet();
				System.out.printf("GSS -> Mensaje recibido: %s%n", mensaje);
				
				// verificar, pw.println()
				String respuesta = protocolo.verificar(mensaje);
				pw.println(respuesta);
				System.out.printf("GSS -> Respuesta enviada: %s%n", respuesta);
				
				if ("#ConexionCerrada#".equals(respuesta)) {
					System.out.printf("GSS -> Conexión se ha finalizado: %s%n", respuesta);
					break;
				}
				
			}

		} catch (Exception e) {
			System.out.printf("GSS -> Error: %s%n", e.getMessage());
		} finally {
			System.out.printf("Estadisticas: Total peticiones: %d | Total clientes aceptados: %d%n",
					ServerTCP.getClientesAceptados().get(), peticionesProcesadas.get());
		}
	}
}