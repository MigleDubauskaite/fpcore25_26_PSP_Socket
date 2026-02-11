package _migle_dubauskaite_examen_socket._8;

public class GestorOperaciones {

	private final static String ERR = "#Formato incorrecto#";
	private final static String FIN = "#Conexion cerrada#";

	private final static String CMD_SUMA = "Suma";
	private final static String CMD_FACTORIAL = "Factorial";
	private final static String CMD_INVERTIR = "Invertir";
	private final static String CMD_SALIR = "Salir";

	public String verificarMensajes(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERR;
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return ERR;

		String contenido = mensaje.substring(1, mensaje.length() - 1);
		String partes[] = contenido.split("#", -1);

		String comando = partes[0];

		if (partes.length == 0 || partes[0].isBlank())
			return ERR;

		switch (comando) {
		case CMD_SUMA:
			if (partes.length == 3)
				return sumar(partes[1], partes[2]);
			else
				return ERR;
		case CMD_FACTORIAL:
			if (partes.length == 2)
				return factorial(partes[1]);
			else
				return ERR;
		case CMD_INVERTIR:
			if (partes.length == 2)
				return invertir(partes[1]);
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

	private static String sumar(String inStr, String fStr) {
		try {
			int inicio = Integer.parseInt(inStr);
			int fin = Integer.parseInt(fStr);
			int suma = inicio + fin;
			return String.valueOf(suma);
		} catch (NumberFormatException e) {
			return ERR;
		}
	}

	private static String factorial(String inStr) {
		try {
			int inicio = Integer.parseInt(inStr);
			if (inicio < 0)
				return ERR;
			long valor = 1;

			for (int i = 1; i <= inicio; i++) {
				valor *= i;
			}
			return String.valueOf(valor);
		} catch (NumberFormatException e) {
			return ERR;
		}
	}

	private static String invertir(String palabra) {
		String invertido = new StringBuilder(palabra).reverse().toString();
		return invertido;
	}
}
