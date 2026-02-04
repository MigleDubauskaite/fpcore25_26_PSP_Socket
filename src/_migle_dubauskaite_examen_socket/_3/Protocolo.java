package _migle_dubauskaite_examen_socket._3;

import java.util.Random;

public class Protocolo {

	private static final String ERR = "#Error#";
	private static final String FIN = "#ConexionCerrada#";

	private static final String CMD_RANDOM = "RANDOM";
	private static final String CMD_TRESNUM = "#TRESNUM#";
	private static final String CMD_CONTARLETRAS = "#CONTARLETRAS#";
	private static final String CMD_INVERTIR = "#INVERTIR#";
	private static final String CMD_FIN = "#FIN#";

	public String verificar(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERR;
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return ERR;

		String limpio = mensaje.substring(1, mensaje.length() - 1);
		String partes[] = limpio.split("#");

		String comando = partes[0].trim();

		if (CMD_FIN.equals(comando)) {
			if (partes.length == 1)
				return FIN;
			else
				return ERR;
		}

		switch (comando) {
		case CMD_RANDOM:
			if (partes.length == 3)
				return generarRandom(partes[1], partes[2]);
			else
				return ERR;
		default:
			return ERR;
		}

	}

	private static String generarRandom(String iStr, String fStr) {
		Random random = new Random();
		int a = Integer.parseInt(iStr);
		int b = Integer.parseInt(fStr);

		int numero = random.nextInt(a, b + 1);

		return String.valueOf(numero);
	}

}
