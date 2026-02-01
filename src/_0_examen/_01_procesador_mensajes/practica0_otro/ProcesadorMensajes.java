package _0_examen._01_procesador_mensajes.practica0_otro;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ProcesadorMensajes {
	
	public String procesarMensajes(String mensaje) {
		
		if(!mensaje.startsWith("#")||!mensaje.endsWith("#")) return "#Error#";
		
		String contenido = mensaje.replaceAll("^#|#$", "");
		
		if(contenido.equals("Saludo")) return "Hola cliente";
		if(contenido.equals("Hora")) {
			LocalTime ahora = LocalTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
            return ahora.format(formato);
		}
		if (contenido.startsWith("Eco#")) return contenido.substring(4);
		if(contenido.equals("Fin")) return "#Finalizado#";

		return "#Error#";
	}

}
