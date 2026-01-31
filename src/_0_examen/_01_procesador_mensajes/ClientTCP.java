package _0_examen._01_procesador_mensajes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClientTCP {

    private String serverIP;
    private int serverPort;
    private static AtomicLong intentos = new AtomicLong(0);

    // MENSAJES POSIBLES
    private static final String[] MENSAJES = {
        "#Listado números#2#11#",
        "#Numero aleatorio#3#20#",
        "Hola",
        "#Fin#"
    };

    private static final int MAX_INTENTOS = 20;

    public ClientTCP(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void iniciarCliente() {

        Random random = new Random();

        try (Socket socket = new Socket(serverIP, serverPort);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {

            for (int i = 1; i <= MAX_INTENTOS; i++) {

                intentos.incrementAndGet();

                String mensaje = MENSAJES[random.nextInt(MENSAJES.length)];

                pw.println(mensaje);
                System.out.println("Intento " + i);
                System.out.println("Mensaje enviado: " + mensaje);

                String respuesta = br.readLine();
                System.out.println("Mensaje recibido: " + respuesta);

                if ("#Error#".equals(respuesta)) {
                    System.out.println("Mensaje no adecuadamente formateado para su tratamiento.");
                }

                System.out.println("-----------------------------");

                if ("#Finalizado#".equals(respuesta)) {
                    System.out.println("Fin de la conexión");
                    break;
                }
            }

            // PRINT FINAL DE INTENTOS
            System.out.println("Total de intentos realizados por el cliente: " + intentos.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // DOS CLIENTES SIMULTÁNEOS
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                ClientTCP client = new ClientTCP("localhost", 8081);
                client.iniciarCliente();
            }).start();
        }
    }
}
