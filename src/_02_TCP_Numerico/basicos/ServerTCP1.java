package _02_TCP_Numerico.basicos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP1 {
	
//	Identifica el servicio o aplicación que utiliza la comunicación TCP.
	private int port;
	
//	Representa la conexión TCP establecida entre cliente y servidor, y permite el envío y recepción de datos.
	private Socket socket;
	
//	Se encarga de escuchar conexiones entrantes en un puerto determinado y aceptar la conexión del cliente.
	private ServerSocket serverSocket;
	
//	Permite recibir datos enviados por el cliente o servidor.
	private InputStream is;
	
//	Permite enviar datos al cliente o servidor.
	private OutputStream os;

	public ServerTCP1(int port) throws IOException {
//		identificar el puerto donde trabajará el servidor
		this.port = port;
		
//		Crea un ServerSocket que queda a la espera de conexiones entrantes de clientes en el puerto especificado.
		serverSocket = new ServerSocket(port);
	}
	
//	Inicia el funcionamiento del servidor
	public void start() throws IOException {
		System.out.printf("[S:%d] → Esperando conexión...%n", port);
		
		socket = serverSocket.accept();
//		El servidor se queda bloqueado (esperando)
//		No continúa hasta que un cliente se conecta a ese puerto.
//		Cuando un cliente se conecta:
//			Se establece la conexión TCP
//			Se crea un Socket nuevo para esa conexión.
//			Ese Socket representa la conexión concreta con ese cliente.
		
		System.out.printf("[S:%d] → ¡Conexión establecida!%n", port);
		
//		Obtiene el flujo de entrada del socket: para leer los datos que envíe el cliente
		is = socket.getInputStream();
		
//		Obtiene el flujo de salida del socket: para enviar datos al cliente
		os = socket.getOutputStream();
	}
	
//	Detiene la comunicación del servidor.
	public void stop() throws IOException {
		System.out.printf("[S:%d] → Cerrando conexión...%n", port);
		
//		Cierra el flujo de entrada: ya no se pueden recibir datos del cliente
		is.close();
		
//		Cierra el flujo de salida: ya no se pueden enviar datos al cliente
		os.close();
		
//		Cierra el socket TCP asociado a ese cliente: 
//		Libera el puerto y los recursos de red usados por esa conexión.
		socket.close();
		
//		Cierra el serverSocket
		serverSocket.close();
		
		System.out.printf("[S:%d] → ¡Conexión cerrada!%n", port);
	}
	
	public static void main(String[] args) {
		
		try {
			ServerTCP1 server = new ServerTCP1(5000);
			server.start();
			
//			Esto lee UN BYTE, no un entero (int).
//			Si el cliente envía 1000, NO llegará bien
			
//			int datoLeido1 = server.is.read();
//			int datoLeido2 = server.is.read();
//			int resultadoADevolver = datoLeido1 + datoLeido2;
//			server.os.write(resultadoADevolver);
			
//			Solución correcta | recomendada (para poder leer enteros):
			DataInputStream dis = new DataInputStream(server.is);
			DataOutputStream dos = new DataOutputStream(server.os);
			
			int datoL1 = dis.readInt();
			int datoL2 = dis.readInt();
			
			System.out.printf("[S:%d] → Datos recibidos de C: %d, %d %n", server.port, datoL1, datoL2);
			
			int resultadoDevolver = datoL1 + datoL2;
			dos.writeInt(resultadoDevolver);
			
			System.out.printf("[S:%d] → Dato enviado a C: %d %n", server.port, resultadoDevolver);
			
			server.stop();
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	

}
