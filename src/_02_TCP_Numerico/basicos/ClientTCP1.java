package _02_TCP_Numerico.basicos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP1 {
	
	private int port;
	private String address;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	public ClientTCP1(String localHost, int port) {
		this.address = localHost;
		this.port = port;
	}
	
//	Inicia el funcionamiento del cliente TCP
	public void start() throws UnknownHostException, IOException {
		System.out.printf("[C: %s:%d] → Solicitando conexión...%n", address, port);
		
//		El cliente inicia la conexión TCP hacia el servidor
		socket = new Socket(address, port);
		
//		Si el servidor está escuchando en ese puerto
//			La conexión se establece
//			Se crea el Socket
		
		System.out.printf("[C: %s:%d] → ¡Conexión establecida!%n", address, port);
		
//		Obtiene el flujo de entrada del socket: para recibir datos enviados por el servidor.
		is = socket.getInputStream();
		
//		Obtiene el flujo de salida del socket: para enviar datos al servidor.
		os = socket.getOutputStream();
	}
	
	public void stop() throws IOException {
		System.out.printf("[C: %s:%d] → Cerrando conexión...%n", address, port);
		
		is.close();
		os.close();
		socket.close();
		
		System.out.printf("[C: %s:%d] → ¡Conexión cerrada!%n", address, port);
	}
	
	public static void main(String[] args) {
		
		ClientTCP1 client = new ClientTCP1("localhost", 5000);
		
		try {
			client.start();
			
			DataOutputStream dos = new DataOutputStream(client.os);
			
			int datoEnviar1 = 20;
			int datoEnviar2 = 10;
			
			dos.writeInt(datoEnviar1);
			dos.writeInt(datoEnviar2);
			
			System.out.printf("[C: %s:%d] → Enviando datos a S: %d, %d %n", client.address, client.port, datoEnviar1, datoEnviar2);
			
			DataInputStream dis = new DataInputStream(client.is);
			
			int datoRecibido = dis.readInt();
			
			System.out.printf("[C: %s:%d] → Dato recibido de S: %d %n", client.address, client.port, datoRecibido);
			
			client.stop();
			
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	

}
