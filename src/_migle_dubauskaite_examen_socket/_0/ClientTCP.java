package _migle_dubauskaite_examen_socket._0;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    private String host;
    private int puerto;
    private static final int MAX_INTENTOS = 5;

    public ClientTCP(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void iniciar() {
        try (Socket socket = new Socket(host, puerto);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {

            System.out.println("==========================================");
            System.out.printf("   CONECTADO - MÁXIMO %d INTENTOS%n", MAX_INTENTOS);
            System.out.println("==========================================");

            int intentos = 0;
            while (intentos < MAX_INTENTOS) {
                System.out.printf("%n(%d/%d) > Comando: ", (intentos + 1), MAX_INTENTOS);
                String linea = sc.nextLine();
                
                pw.println(linea);
                intentos++;

                String respuesta = br.readLine();
                System.out.printf("[Server]: %s%n", respuesta);

                if (GestorInstrucciones.FIN.equals(respuesta)) return;
            }

            System.out.printf("%n[!] Límite alcanzado. Enviando cierre...%n");
            pw.println("#FIN#"); 

        } catch (IOException e) {
            System.err.printf("[Error]: No se pudo conectar a %s:%d%n", host, puerto);
        }
    }

    public static void main(String[] args) {
		new ClientTCP("localhost", 8070).iniciar();
	}
}