package _1_TCP_numerico;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketNumericoC {

	private int port;
	private String address;
	private Socket socket;
	private InputStream is;
	private OutputStream os;

	public ClientSocketNumericoC(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void start() throws UnknownHostException, IOException {

		System.out.printf("[CLIENT: %s:%d] Solicitando conexión...%n", address, port);

		socket = new Socket(address, port);

		System.out.printf("[CLIENT: %s:%d] Conexión establecida.%n", address, port);

		is = socket.getInputStream();
		os = socket.getOutputStream();

	}

	public void stop() throws IOException {

		System.out.printf("[CLIENT: %s:%d] Cerrando conexión...%n", address, port);

//		is.close();
//		os.close();
		socket.close();

		System.out.printf("[CLIENT: %s:%d] Conexión cerrada.%n", address, port);
	}

	public static void main(String[] args) {

		ClientSocketNumericoC client = new ClientSocketNumericoC("localhost", 8081);

		try {
			
			client.start();
			
			for (int datoAEnviar = 0; datoAEnviar < 255; datoAEnviar++) {
				
//				mandando un dado, esperando ser recibido y mostrandolo
				
				client.os.write(datoAEnviar);
				System.out.printf("[CLIENT: %s:%d] Dato enviado (%d) .%n", client.address, client.port, datoAEnviar);
				
				int datoRecibido = client.is.read();
				System.out.printf("[CLIENT: %s:%d] Dato recibido (%d) .%n", client.address, client.port, datoRecibido);
			}

			client.stop();

		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
