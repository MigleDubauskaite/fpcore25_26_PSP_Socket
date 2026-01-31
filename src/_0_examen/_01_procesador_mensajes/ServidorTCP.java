package _0_examen._01_procesador_mensajes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {

	private ServerSocket serverSocket;
	private int port;
	private static long socketAceptados = 0;

	public ServidorTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	public static long getSocketAceptados() {
		return socketAceptados;
	}

	public static void main(String[] args) {

		ServidorTCP servidor = null;

		try {
			servidor = new ServidorTCP(8081);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {

			Socket socket = null;

			try {
				socket = servidor.serverSocket.accept();
				System.out.printf("(Servidor) Cliente conectado en puerto %d%n", servidor.port);
				socketAceptados++;
				new Thread(new GestorSocketServer(socket)).start();
				;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
