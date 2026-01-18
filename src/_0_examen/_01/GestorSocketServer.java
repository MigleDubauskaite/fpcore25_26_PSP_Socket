package _0_examen._01;

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
	
	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		// creamos instancia del juego
	}

	@Override
	public void run() {
		
		try (InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));){
			
			String datoRecibido;
			
			while((datoRecibido = br.readLine())!= null) {
				peticionesAlServidor.getAndIncrement();
				
			}
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		
	}
	
	
}
