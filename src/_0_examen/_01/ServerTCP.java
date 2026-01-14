package _0_examen._01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

	private int port;
	private Socket socket;
	private ServerSocket serverSocket;
	private InputStream is;
	private OutputStream os;

	public ServerTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket();
	}

	public void start() throws IOException {

		System.out.printf("[S:%d] → Estableciendo conexión...%n", port);
		socket = serverSocket.accept();
		System.out.printf("[S:%d] → Conexión establecida!%n", port);

		is = socket.getInputStream();
		os = socket.getOutputStream();
	}

	public void stop() throws IOException {
		System.out.printf("[S:%d] → Cerrando conexión...%n", port);
		is.close();
		os.close();
		serverSocket.close();
		socket.close();
		System.out.printf("[S:%d] → Conexión cerrada%n", port);
	}

	public static void main(String[] args) {

		try {
			ServerTCP server = new ServerTCP(5000);
			server.start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
