package _1_TCP_texto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketTextsNoCiclico {

//	👉 Es no cíclico:
//	solo una petición → una respuesta → se cierra el socket.

	/*
	 * Se conecta al servidor. Envía un texto (por ejemplo "hola"). Recibe la
	 * respuesta del servidor ("HOLA"). Muestra el resultado. Cierra la conexión.
	 */

//	El socket que conecta con el servidor
	private Socket socket;

//	Canales de bytes
	private InputStream is; // Para leer datos que vienen del servidor
	private OutputStream os; // Para enviar datos al servidor

	private String direccion;
	private int puerto;

//	canales del texto
	private PrintWriter pw; // Para escribir texto en el socket
	private InputStreamReader isr; // Para convertir bytes en caracteres
	private BufferedReader br; // Para leer líneas completas de texto

//	Permite crear un cliente indicando a qué servidor y puerto conectarse.
	public ClientSocketTextsNoCiclico(String direccion, int puerto) {
		this.direccion = direccion; // IP o nombre del servidor
		this.puerto = puerto; // Puerto donde escucha el servidor
	}

//	Conectar al servidor
	public void start() throws UnknownHostException, IOException {
		System.out.println("(Cliente) Lanzando petición socket ...");

//		Crea el socket y se conecta al servidor
		socket = new Socket(direccion, puerto);

//		Inicializa los canales de bytes
		is = socket.getInputStream(); // Para leer lo que envía el servidor
		os = socket.getOutputStream(); // Para enviar datos al servidor
	}

//	Hasta aquí no hay texto “legible”, solo bytes crudos.

//	Abrir canales de texto
	public void abrirCanalesTexto() {

		// PrintWriter permite escribir texto en el socket
		pw = new PrintWriter(os, true);

		// Convierte los bytes que vienen del servidor en caracteres
		isr = new InputStreamReader(is);

		// Permite leer líneas completas de texto
		br = new BufferedReader(isr);

	}

//	Cerrar lectores/escritores de texto
	public void cerrarCanalesTexto() throws IOException {
		pw.close();
		br.close();
		isr.close();
	}

//	Cerrar flujos de bytes y el socket
	public void stop() throws IOException {
		os.close();
		is.close();
		socket.close();
	}

	public static void main(String[] args) {

		ClientSocketTextsNoCiclico cliente = new ClientSocketTextsNoCiclico("localhost", 8081);

		try {
//			Conectar al servidor
			cliente.start();

//			Abrir canales de texto para leer y escribir
			cliente.abrirCanalesTexto();

			String datoEnviado = "hola";
			cliente.pw.println(datoEnviado); // Enviar texto al servidor

//			Esperar y leer la respuesta del servidor
			String datoRecibido = cliente.br.readLine();
			System.out.println("(Cliente) Recibido: " + datoRecibido);

			cliente.cerrarCanalesTexto();
			cliente.stop();

		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
