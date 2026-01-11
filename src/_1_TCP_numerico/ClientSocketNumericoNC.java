package _1_TCP_numerico;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.random.RandomGenerator;

public class ClientSocketNumericoNC {

	private int port;
	private String address;
	private Socket socket;
	private InputStream is;
	private OutputStream os;

	private final static RandomGenerator random = RandomGenerator.getDefault();

	public ClientSocketNumericoNC(String address, int port) {
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

		is.close();
		os.close();
		socket.close();

		System.out.printf("[CLIENT: %s:%d] Conexión cerrada.%n", address, port);
	}

	public static void main(String[] args) {

		ClientSocketNumericoNC client = new ClientSocketNumericoNC("localhost", 8081);

		try {
			
			client.start();
			
			int datoAEnviar = random.nextInt(0, 256);
			client.os.write(datoAEnviar);
			
			System.out.printf("[CLIENT: %s:%d] Dato enviado (%d) .%n", client.address, client.port, datoAEnviar);

			int datoRecibido = client.is.read();
			
			System.out.printf("[CLIENT: %s:%d] Dato recibido (%d) .%n", client.address, client.port, datoRecibido);

			client.stop();

		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
