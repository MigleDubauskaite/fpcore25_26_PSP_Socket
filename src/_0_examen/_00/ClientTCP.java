package _0_examen._00;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * La clase ClienteTCP implementa un cliente TCP básico. Esta clase es
 * responsable de establecer una conexión con el servidor, enviar solicitudes y
 * recibir respuestas.
 */
public class ClientTCP {

	private Socket socket;

	private String serverIP;
	private int serverPort;

	private OutputStream os;
	private InputStream is;
	private PrintWriter pw;
	private InputStreamReader isr;
	private BufferedReader br;

	private int intentos;

	/**
	 * Constructor de ClienteTCP.
	 * 
	 * @param serverIP   La dirección IP del servidor al que se conectará el
	 *                   cliente.
	 * @param serverPort El puerto del servidor al que se conectará el cliente.
	 * @throws UnknownHostException Si la dirección IP del host no se puede
	 *                              determinar.
	 * @throws IOException          Si ocurre un error de E/S al abrir la conexión.
	 */

	public ClientTCP(String serverIP, int serverPort) throws UnknownHostException, IOException {
		this.serverIP = serverIP;
		this.serverPort = serverPort;

		socket = new Socket(serverIP, serverPort);

		is = socket.getInputStream();
		os = socket.getOutputStream();
		pw = new PrintWriter(os);
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
	}

	public static void main(String[] args) {

		ClientTCP cliente;

		for (int lanzamientoClientes = 0; lanzamientoClientes < 5; lanzamientoClientes++) {

			try {
				cliente = new ClientTCP("localhost", 8081);

				for (int i = 0; i < 10; i++) {
					String datoEnviado = Integer.toString(i);
					cliente.pw.println(datoEnviado);

					String datoRecibido = cliente.br.readLine();
					cliente.intentos++;

					if ("Acertado".equals(datoRecibido)) {
						System.out.printf("(Cliente: %s:%d) Dato acertado después de %d intentos%n", cliente.serverIP,
								cliente.serverPort, cliente.intentos);
						break;
					}
				}

				cliente.pw.close();
				cliente.br.close();
				cliente.is.close();
				cliente.os.close();
				cliente.isr.close();

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
