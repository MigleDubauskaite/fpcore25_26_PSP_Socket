package _migle_dubauskaite_examen_socket._0;

import java.util.Random;

public class GestorInstrucciones {

	public static final String ERR = "#Error#";
	public static final String FIN = "#Finalizado#";

	public static final String CMD_LISTA = "LISTADO NÚMEROS";
	public static final String CMD_ALEATORIO = "NUMERO ALEATORIO";
	public static final String CMD_FIN = "FIN";

	public String verificar(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERR;
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return ERR;

		String limpioContenido = mensaje.substring(1, mensaje.length() - 1).toUpperCase();

		String partes[] = limpioContenido.split("#");

		if (partes.length == 0)
			return ERR;

		String comando = partes[0];

		switch (comando) {
		case CMD_FIN:
			return (partes.length == 1) ? FIN : ERR;
		case CMD_LISTA:
			return (partes.length == 3) ? listarNumeros(partes[1], partes[2]) : ERR;
		case CMD_ALEATORIO:
			return (partes.length == 3) ? devolverNumeroRandom(partes[1], partes[2]) : ERR;
		default:
			return ERR;
		}
	}

	private static String listarNumeros(String inicio, String fin) {

		StringBuilder sb = new StringBuilder();

		try {
			int a = Integer.parseInt(inicio);
			int b = Integer.parseInt(fin);
			if (a > b)
				return ERR;

			for (int i = a; i <= b; i++) {
				sb.append(i);
				if (i < b)
					sb.append("|");
			}
			return sb.length() == 0 ? ERR : sb.toString();
		} catch (NumberFormatException e) {
			return ERR;
		}

	}

	private static String devolverNumeroRandom(String inicioS, String finS) {

		try {
			Random random = new Random();
			int inicio = Integer.parseInt(inicioS);
			int fin = Integer.parseInt(finS);
			if (inicio >= fin)
				return ERR;

			int numero = random.nextInt(inicio, fin + 1);
			return String.valueOf(numero);

		} catch (NumberFormatException e) {
			return ERR;
		}

	}

}
