package _0_examen._01_procesador_mensajes.practica1_otro;

public class Calculadora {

	public String procesarMensaje(String mensaje) {

		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return "#ERROR#";

		if (mensaje.equals("#FIN#"))
			return "#ADIOS#";
		
		String limpio = mensaje.replaceAll("^#|#$", "");

		String partes[] = limpio.split("#");

		if (partes.length != 3)
			return "#ERROR#";

		String operacion = partes[0];
		int a = Integer.parseInt(partes[1]);
		int b = Integer.parseInt(partes[2]);

		switch (operacion) {
		case "SUMA": {
			return String.valueOf(a + b);
		}
		case "RESTA": {
			return String.valueOf(a - b);
		}
		case "MULT": {
			return String.valueOf(a * b);
		}
		case "DIV": {
			if (b == 0)
				return "#ERROR#";
			return String.valueOf(a / b);
		}
		default:
			return "#ERROR#";
		}

	}

}
