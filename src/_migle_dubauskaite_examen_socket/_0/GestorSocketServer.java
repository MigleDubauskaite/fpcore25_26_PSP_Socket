package _migle_dubauskaite_examen_socket._0;

import java.io.*;
import java.net.Socket;

public class GestorSocketServer implements Runnable {
    private final Socket socket;
    private GestorInstrucciones gestor;

    public GestorSocketServer(Socket socket) {
        this.socket = socket;
        this.gestor = new GestorInstrucciones();
    }

    @Override
    public void run() {
        try (socket; 
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String mensaje;
            while ((mensaje = br.readLine()) != null) {
                System.out.printf("[Gestor] Comando recibido: %s%n", mensaje);
                
                String respuesta = gestor.verificar(mensaje);
                pw.println(respuesta);

                if (GestorInstrucciones.FIN.equals(respuesta)) break; 
            }
        } catch (IOException e) {
            System.err.printf("[Gestor Error]: %s%n", e.getMessage());
        } finally {
            System.out.printf("--- Conexión terminada. Total histórico: %d ---%n", 
                              ServerTCP.getContadorPeticiones().get());
        }
    }
}