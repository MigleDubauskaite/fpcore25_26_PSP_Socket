package _0_examen._00;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Esta clase inicia el servidor en un puerto específico y espera conexiones de
 * clientes. Por cada cliente que se conecta, crea un hilo utilizando
 * GestorSocketServer.
 */
public class ServidorTCP {

	private ServerSocket serverSocket;
	private int port;
	private static long socketAceptados = 0;

	/**
	 * Constructor de ServidorTCP.
	 * 
	 * @param port El puerto en el que el servidor escuchará las conexiones
	 *             entrantes.
	 * @throws IOException Si ocurre un error de E/S al abrir el puerto del
	 *                     servidor.
	 */
	public ServidorTCP(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	/**
	 * Obtiene el número total de conexiones de socket aceptadas por este servidor.
	 */
	public static long getSocketAceptados() {
		return socketAceptados;
	}

	/**
	 * Punto de entrada principal del programa. Este método inicia el servidor en el
	 * puerto 8081. Entra en un bucle infinito para aceptar y manejar conexiones de
	 * clientes. Maneja las excepciones de E/S relacionadas con la creación del
	 * servidor y la aceptación de conexiones.
	 */
	public static void main(String[] args) {

		ServidorTCP servidor = null;

		try {
			servidor = new ServidorTCP(8081);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			Socket miSocket = null;

			try {
				System.out.printf("(Server: %d)Esperando la conexión...%n", servidor.port);
				miSocket = servidor.serverSocket.accept();
				System.out.printf("(Server: %d)Socket aceptado.%n", servidor.port);
				socketAceptados++;
				new Thread(new GestorSocketServer(miSocket)).start();
			} catch (IOException e) {
				e.printStackTrace();
				return; // si algo ha ido mal finaliza el método
			}
		}

	}

}
