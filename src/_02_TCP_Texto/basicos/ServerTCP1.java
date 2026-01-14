package _02_TCP_Texto.basicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP1 {
	
	private ServerSocket serverSocket;
	private Socket socket;
	
	private InputStream is;
	private OutputStream os;
	
	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;
	
	public ServerTCP1(int puerto) throws IOException {
		serverSocket = new ServerSocket(puerto);
	}
	
	public void start() throws IOException {
		System.out.printf("[S] → Esperando conexión...%n");
		
		socket = serverSocket.accept();
		
		System.out.printf("[S] → ¡Conexión establecida!...%n");
		
		is = socket.getInputStream();
		os = socket.getOutputStream();		
	}
	
	public void stop() throws IOException {
		System.out.printf("[S] → Cerrando conexión...%n");
		
		is.close();
		os.close();
		serverSocket.close();
		socket.close();
		
		System.out.printf("[S] → ¡Conexión cerrada!%n");
	}
	
	public void abrirCanalesTexto() {
		
		System.out.println("[S] → Abriendo canales de texto...");
		
//		Permite enviar texto al cliente
		pw = new PrintWriter(os, true);
		
//		Convierte bytes → caracteres
		isr = new InputStreamReader(is);
		
//		Permite leer texto línea a línea (readLine())
		br = new BufferedReader(isr);
		
		System.out.println("[S] → ¡Canales de texto abiertos!");
	}
	
	public void cerrarCanalesTexto() throws IOException {
		System.out.println("[S] → Cerrando canales de texto...");
		
		pw.close();
		isr.close();
		br.close();
		
		System.out.println("[S] → ¡Canales de texto cerrados!");
	}
	
	public static void main(String[] args) {
		
		try {
			ServerTCP1 server = new ServerTCP1(4000);
			
			server.start();
			server.abrirCanalesTexto();
			
			String datoLeido = server.br.readLine();
			System.out.printf("[S] → Dato recibido de C: %s %n", datoLeido);
			
			String mensajeEnviar = "Mensaje recibido correctamente";
			server.pw.println(mensajeEnviar);
			System.out.printf("[S] → Dato enviado a C: %s %n", mensajeEnviar);
			
			server.cerrarCanalesTexto();
			server.stop();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
