package _migle_dubauskaite_examen_socket._0;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServerTCP {

    private int puerto;
    private static AtomicLong contadorPeticiones = new AtomicLong(0);

    public ServerTCP(int puerto) {
        this.puerto = puerto;
    }

    public static AtomicLong getContadorPeticiones() {
        return contadorPeticiones;
    }

    public void ejecutar() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.printf("--- SERVIDOR INICIADO (Puerto: %d) ---%n", puerto);

            while (true) {
                Socket socket = serverSocket.accept();
                contadorPeticiones.incrementAndGet();
                System.out.printf("[Server] Nueva conexión desde: %s%n", puerto);

                new Thread(new GestorSocketServer(socket)).start();
            }
        } catch (IOException e) {
            System.err.printf("[Server Error]: %s%n", e.getMessage());
        }
    }

    public static void main(String[] args) {
        ServerTCP servidor = new ServerTCP(8070);
        servidor.ejecutar();
    }
}