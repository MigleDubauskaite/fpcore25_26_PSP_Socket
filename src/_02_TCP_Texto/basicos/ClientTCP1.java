package _02_TCP_Texto.basicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP1 {
	
	private String address;
	private int port;
	
	private Socket socket;
	
	private InputStream is;
	private OutputStream os;
	
	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;
	
	public ClientTCP1(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void start() throws UnknownHostException, IOException {
		
		System.out.printf("[C: %s:%d] → Solicitando conexión...%n", address, port);
		
		socket = new Socket(address, port);
		
		is = socket.getInputStream();
		os = socket.getOutputStream();	
		
		System.out.printf("[C: %s:%d] → ¡Conexión establecida!%n", address, port);
	}
	
	public void stop() throws IOException {
		System.out.printf("[C: %s:%d] → Cerrando conexión...%n", address, port);
		
		is.close();
		os.close();
		socket.close();
		
		System.out.printf("[C: %s:%d] → ¡Conexión cerrada!%n", address, port);
	}
	
	public void abrirCanalesTexto() {
		System.out.println("[C] → Abriendo canales de texto...");
		
		pw = new PrintWriter(os, true);
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		
		System.out.println("[C] → ¡Canales de texto abiertos!");
	}
	
	public void cerrarCanalesTexto() throws IOException {
		System.out.println("[C] → Cerrando canales de texto...");
		
		pw.close();
		isr.close();
		br.close();
		
		System.out.println("[C] → ¡Canales de texto cerrados!");
	}
	
	public static void main(String[] args) {
		
		ClientTCP1 client = new ClientTCP1("localhost", 4000);
		
		try {
			client.start();
			client.abrirCanalesTexto();
			
			String datoEnviar = "HOLA";
			client.pw.println(datoEnviar);
			System.out.printf("[C] → Dato enviado a S: %s %n", datoEnviar);
			
			String datoRecibido = client.br.readLine();
			System.out.printf("[C] → Dato recibido de S: %s %n", datoRecibido);
			
			client.cerrarCanalesTexto();
			client.stop();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
