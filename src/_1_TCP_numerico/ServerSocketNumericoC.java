package _1_TCP_numerico;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketNumericoC {

	private int port;
	private Socket socket;
	private ServerSocket serverSocket;
	private InputStream is;
	private OutputStream os;

	public ServerSocketNumericoC(int port) throws IOException {
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

			ServerSocketNumericoC server = new ServerSocketNumericoC(8081);
			server.start();

			// bucle infinito hasta que se corte la conexión
			while (true) {
				int datoLeido = server.is.read();
				
				// -1 es el ultimo dato enviado
				if(datoLeido == -1) break;
				
				System.out.printf("[SERVER: %d] Dato recibido (%d) .%n", server.port, datoLeido);

				int datoADevolver = datoLeido + 1; // devuelvo este dato sumando uno
				server.os.write(datoADevolver);
				System.out.printf("[SERVER: %d] Dato enviado (%d) .%n", server.port, datoADevolver);
			}

			server.stop();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
