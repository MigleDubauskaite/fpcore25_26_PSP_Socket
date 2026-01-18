package _0_examen._000Correcto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//ABRIR PUERTO + ESPERAR CLIENTES + ACEPTAR CONEXIONES + CREAR HILO POR CADA CLIENTE
public class ServidorTCP {

	private ServerSocket serverSocket;
	private int port;
	private static long socketAceptados = 0;

	public ServidorTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port); // CREA UN SERVER SOCKET
	}

	public static long getSocketAceptados() {
		return socketAceptados;
	}

	public static void main(String[] args) {

		ServidorTCP servidor = null;

		try {
			servidor = new ServidorTCP(8080); // CREA UN SERVER SOCKET EN PUERTO 8080
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			Socket miSocket = null;

			try {
				System.out.printf("(Server: %d)Esperando la conexión...%n", servidor.port);
				miSocket = servidor.serverSocket.accept(); // ESPERA A QUE UN CLIENTE SE CONECTE
				System.out.printf("(Server: %d)Socket aceptado.%n", servidor.port);
				socketAceptados++;
				new Thread(new GestorSocketServer(miSocket)).start(); // CREA UN NUEVO HILO CON GESTOR_SOCKET_SERVER
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

	}

}
