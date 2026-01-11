package _1_TCP_texto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTextsNoCiclico {

//	👉 Es no cíclico:
//	solo una petición → una respuesta → se cierra el socket.

	/*
	 * Espera a que un cliente se conecte. Recibe una línea de texto. La convierte a
	 * mayúsculas. La envía de vuelta al cliente. Cierra la conexión.
	 */

	private ServerSocket serverSocket; // escucha conexiones
	private Socket socket; // conexión con un cliente

	private InputStream is; // entrada binaria
	private OutputStream os; // salida binaria

//	canales del texto
	private PrintWriter pw; // escritura de texto
	private InputStreamReader isr; // lectura de texto
	private BufferedReader br; // lectura de texto

//	CONSTRUCTOR:
//	Abre el puerto 8081
//	El servidor queda listo para aceptar clientes
	public ServerSocketTextsNoCiclico(int puerto) throws IOException {
		serverSocket = new ServerSocket(puerto);
	}

//	Arrancar el servidor
	public void start() throws IOException {

		System.out.println("(Servidor) Esperando conexión ...");

		socket = serverSocket.accept(); // bloquea el programa hasta que llegue un cliente
		System.out.println("(Servidor) Conexión establecida ...");

//		Cuando llega un cliente: 
//		Se crea el Socket
//		Se abren los flujos binarios

//		Al crear un Socket, Java establece dos flujos de comunicación (entrada y salida).
//		Con getInputStream() y getOutputStream() se obtienen esos flujos para poder leer y escribir datos.

//		obtienen una referencia para poder usarlas
		is = socket.getInputStream(); // Dame la tubería por donde voy a LEER lo que manda el otro”
		os = socket.getOutputStream(); // Dame la tubería por donde voy a ENVIAR datos al otro”
	}

//	Abrir canales de texto
	public void abrirCanalesTexto() {

		System.out.println("(Servidor) Abriendo canales de texto ...");

		pw = new PrintWriter(os, true); // autoflush
//		****** true → envía el texto inmediatamente
//		PrintWriter es una clase que:
		/*
		 * Escribe TEXTO Convierte automáticamente texto → bytes Envía esos bytes por el
		 * OutputStream
		 */

		isr = new InputStreamReader(is);
		/*
		 * is → flujo de bytes que llega del socket. InputStreamReader (isr) → convierte
		 * esos bytes en caracteres (char) que Java puede entender como texto. Es como
		 * un traductor entre el lenguaje de la máquina (bytes) y el lenguaje humano
		 * (letras y símbolos).
		 */
//		Sin InputStreamReader, tendríamos que convertir los bytes a caracteres nosotros mismos.

		br = new BufferedReader(isr);
		/*
		 * Leer carácter por carácter del InputStreamReader. Seguir leyendo hasta
		 * encontrar un salto de línea (\n) o el fin del stream. Devolver un String con
		 * esos caracteres
		 */

		System.out.println("(Servidor) Canales de texto abiertos ...");
	}

//	Cierre de canales de texto
	public void cerrarCanalesTexto() throws IOException {

		System.out.println("(Servidor) Cerrando canales de texto ...");

		pw.close();
		br.close();
		isr.close();

		System.out.println("(Servidor) Canales de texto cerrados.");
	}

//	Cerrar socket y servidor
	public void stop() throws IOException {
		System.out.println("(Servidor) Cerrando conexión ...");

		is.close();
		os.close();
		socket.close();
		serverSocket.close();

		System.out.println("(Servidor) Conexión cerrada.");
	}

	public static void main(String[] args) {

		try {
			ServerSocketTextsNoCiclico server = new ServerSocketTextsNoCiclico(8081);

			server.start();
			server.abrirCanalesTexto();

			String datoLeido = server.br.readLine();
			System.out.printf("(Servidor) Leido: %s%n", datoLeido);

			String datoAMandar = datoLeido.toUpperCase();
			server.pw.println(datoAMandar); // Escribe este texto en el socket y lo envia al cliente
			System.out.printf("(Servidor) Mandado: %s%n", datoAMandar);
			
			server.cerrarCanalesTexto();
			server.stop();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
