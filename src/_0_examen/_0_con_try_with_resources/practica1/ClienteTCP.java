package _0_examen._0_con_try_with_resources.practica1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;
import java.util.random.RandomGenerator;

public class ClienteTCP {

	private String serverIP;
	private int serverPort;
	private static AtomicLong intentos = new AtomicLong(0);
	
	private static RandomGenerator random = RandomGenerator.getDefault();

	public ClienteTCP(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public void iniciarCliente() {

		try (Socket socket = new Socket(serverIP, serverPort);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			for (int i = 0; i < 1_000; i++) {
				
				int intentoCliente = random.nextInt(1, 101);
				pw.println(intentoCliente);
				intentos.getAndIncrement();

				System.out.println("(C) → Enviado: " + intentoCliente);
				  
				String respuesta = br.readLine();
				System.out.println("(C) Servidor respondió: " + respuesta);

				if ("Acertado".equals(respuesta)) {
					System.out.printf("(C) -> Dato acertado después de %d intentos %n", 
							intentos.get());
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			ClienteTCP cliente = new ClienteTCP("localhost", 8080);
			cliente.iniciarCliente();
		}
	}

}
