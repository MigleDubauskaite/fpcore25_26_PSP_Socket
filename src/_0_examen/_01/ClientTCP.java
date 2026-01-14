package _0_examen._01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientTCP {
	
	private int port;
	private String address;
	private Socket socket;
	private ServerSocket serverSocket;
	private InputStream is;
	private OutputStream os;
	
	public ClientTCP(String address, int port) {
		this.port = port;
		this.address = address;
	}
	
	public void start() throws IOException {
		
		System.out.printf("[C:%d] → Estableciendo conexión...%n", port);
		socket = serverSocket.accept();
		System.out.printf("[C:%d] → Conexión establecida!%n", port);
		
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}
	
	public void stop() throws IOException {
		System.out.printf("[C:%d] → Cerrando conexión...%n", port);
		is.close();
		os.close();
		serverSocket.close();
		socket.close();
		System.out.printf("[C:%d] → Conexión cerrada%n", port);
	}
	
	public static void main(String[] args) {
		
		
		
	}
	
	

}
