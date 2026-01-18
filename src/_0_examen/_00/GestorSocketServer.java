package _0_examen._00;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

import _0_examen._000Correcto.JuegoAdivinaNumero;

/**
 * GestorSocketServer es una clase que implementa la interfaz Runnable para
 * manejar la comunicación con un cliente en un hilo separado. Esta clase se
 * encarga de abrir los streams de entrada y salida, leer los datos enviados por
 * el cliente, procesarlos y enviar una respuesta.
 */
public class GestorSocketServer implements Runnable {

	private JuegoAdivinaNumero juego;

	private Socket socket;

	private OutputStream os;
	private InputStream is;
	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;

	/*
	 * AtomicLong se utiliza cuando necesitas trabajar con un long compartido entre
	 * varios hilos y quieres hacerlo de forma segura sin usar synchronized.
	 */
	/*
	 * Ejemplo real: Número de clientes conectados Peticiones recibidas por un
	 * servidor IDs únicos Mensajes procesados
	 */
	private static AtomicLong peticionesAlServidor = new AtomicLong(0);

//	@param socket El socket conectado al cliente.
	public GestorSocketServer(Socket socket) {
		this.socket = socket;
		juego = new JuegoAdivinaNumero();
	}

	/**
	 * Método run que se ejecuta cuando el hilo del servidor se inicia. Este método
	 * configura los streams de comunicación y procesa los mensajes recibidos del
	 * cliente. Responde a cada mensaje y cierra la conexión cuando se completa la
	 * interacción.
	 */

	@Override
	public void run() {
//		Configuración de streams y manejo de la comunicación
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
		} catch (IOException e) {
			System.err.println("(Gestor sockets): Error abriendo streams del socket");
			return; // Finaliza el método si no se pueden abrir los streams
		}

		while (true) {
			String datoLeido;

			try {
				datoLeido = br.readLine();
				peticionesAlServidor.getAndIncrement();

				if (datoLeido != null) {
					String respuesta = juego.verificarIntento(datoLeido);
					pw.println(respuesta);

					if ("Acertado".equals(respuesta)) {
						System.out.printf("(Gestor sockets) → sockets aceptados: %d | peticiones recibidas: %d %n",
								ServidorTCP.getSocketAceptados(), peticionesAlServidor.get());
						break; // salir del bucle si el cliente acierta el número
					}
				} else {
					break; // salir del bucle si el cliente cierra la conexión
				}

			} catch (IOException e) {
				System.err.println("(Gestor sockets): Error leyendo el buffered reader");
				break;
			}
		}

//		cierre de recursos
		try {
			is.close();
			os.close();
			pw.close();
			isr.close();
			br.close();
		} catch (IOException e) {
			System.err.println("(Gestor sockets): Error cerrando streams del socket");
		}

	}

}
