package _migle_dubauskaite_examen_socket._9;

import java.util.Random;

public class GestorMensajes {

	private final static String ERR = "#Formato incorrecto#";
	private final static String FIN = "#Conexion cerrada#";

	private final static String CMD_MULTIPLICAR = "Multiplicar";
	private final static String CMD_RANDOM = "Aleatorio";
	private final static String CMD_INVERTIR = "Invertir";
	private final static String CMD_SALIR = "Salir";

	public String verificarMensaje(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERR;
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return ERR;

		String contenido = mensaje.substring(1, mensaje.length() - 1);
		String partes[] = contenido.split("#", -1);

		String comando = partes[0];

		if (partes.length == 0)
			return ERR;

		switch (comando) {
		case CMD_MULTIPLICAR:
			if (partes.length == 3)
				return multiplicar(partes[1], partes[2]);
			else
				return ERR;
		case CMD_RANDOM:
			if (partes.length == 4)
				return generarNumRandom(partes[1], partes[2], partes[3]);
			else
				return ERR;
		case CMD_INVERTIR:
			if (partes.length == 2)
				return invertirPalabra(partes[1]);
			else
				return ERR;
		case CMD_SALIR:
			if (partes.length == 1)
				return FIN;
			else
				return ERR;
		default:
			return ERR;
		}

	}

	private static String multiplicar(String num1, String num2) {
		try {
			int a = Integer.parseInt(num1);
			int b = Integer.parseInt(num2);
			if (a == 0 || b == 0)
				return ERR;
			int multiplicacion = a * b;
			return String.valueOf(multiplicacion);
		} catch (NumberFormatException e) {
			return ERR;
		}
	}

	private static String generarNumRandom(String min, String max, String cantidad) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		try {
			int a = Integer.parseInt(min);
			int b = Integer.parseInt(max);
			int cant = Integer.parseInt(cantidad);
			int numero;

			for (int i = 1; i <= cant; i++) {
				numero = random.nextInt(a, b + 1);
				sb.append(numero);
				if (i < cant)
					sb.append("|");
			}
			return sb.toString();
		} catch (NumberFormatException e) {
			return ERR;
		}
	}

	private static String invertirPalabra(String texto) {
		return new StringBuilder(texto).reverse().toString();
	}

}
