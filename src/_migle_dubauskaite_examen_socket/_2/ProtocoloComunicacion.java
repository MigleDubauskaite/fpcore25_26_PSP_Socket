package _migle_dubauskaite_examen_socket._2;

import java.util.Random;

public class ProtocoloComunicacion {

	private static final String ERR = "#Error#";
	private static final String MEN_FIN = "#ConexionCerrada#";

	private static final String CMD_RANDOM = "RANDOM3";
	private static final String CMD_SUMA = "SUMA";
	private static final String CMD_VOCALES = "VOCALES";
	private static final String CMD_MAYUS = "MAYUS";
	private static final String CMD_FIN = "FIN";

	public String procesar(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERR;
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return ERR;

		String contenido = mensaje.substring(1, mensaje.length() - 1).toUpperCase();
		String partes[] = contenido.split("#");

		String comando = partes[0];

		if (CMD_FIN.equals(comando))
			return MEN_FIN;

		if (partes.length == 3) {
			switch (comando) {
			case CMD_RANDOM:
				return generar3NumAleatorios(partes[1], partes[2]);
			default:
				return ERR;
			}
		}

		return ERR;
	}

	private static String generar3NumAleatorios(String numIn, String numFin) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		try {
			int numA = Integer.parseInt(numIn);
			int numB = Integer.parseInt(numFin);
			int numero;
			for (int i = 0; i < 3; i++) {
				numero = random.nextInt(numA, numB + 1);
				sb.append(numero);
				if (i < 2)
					sb.append("|");
			}
			return sb.toString();
		} catch (NumberFormatException e) {
			return ERR;
		}

	}
}
