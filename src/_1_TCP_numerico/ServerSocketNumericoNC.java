package _1_TCP_numerico;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketNumericoNC {

	/*
	 * Crear un Socket Server que esté preparado para recibir un número (de entre 0
	 * y 254) y devuelva el número siguiente.
	 */

	private int port; // puerto de server
	private Socket socket;
	private ServerSocket serverSocket;
	private InputStream is;
	private OutputStream os;

	public ServerSocketNumericoNC(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	public void start() throws IOException {

		System.out.printf("[SERVER: %d] Esperando conexión...%n", port);

		socket = serverSocket.accept();

		System.out.printf("[SERVER: %d] Conexión establecida.%n", port);

		is = socket.getInputStream();
		os = socket.getOutputStream();
	}

	public void stop() throws IOException {

		System.out.printf("[SERVER: %d] Cerrando conexiones...%n", port);

		is.close();
		os.close();
		socket.close();

		System.out.printf("[SERVER: %d] Conexión cerrada.%n", port);
	}

	public static void main(String[] args) {

		try {
			ServerSocketNumericoNC server = new ServerSocketNumericoNC(8081);

			server.start();
			int datoLeido = server.is.read(); // esperamos al dato que llega por el socket

			System.out.printf("[SERVER: %d] Dato recibido (%d) .%n", server.port, datoLeido);

			int datoADevolver = datoLeido + 1; // devuelvo este dato sumando uno
			server.os.write(datoADevolver);

			System.out.printf("[SERVER: %d] Dato enviado (%d) .%n", server.port, datoADevolver);

			server.stop();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
