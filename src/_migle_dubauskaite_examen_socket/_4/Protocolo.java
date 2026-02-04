package _migle_dubauskaite_examen_socket._4;

import java.util.Random;

public class Protocolo {

	private static final String ERR = "#Error#";
	private static final String FIN = "#ConexionCerrada#";

	private static final String CMD_LISTANUM = "LISTANUM";
	private static final String CMD_ABECEDARIO = "ABECEDARIO";
	private static final String CMD_RANDOM = "RANDOM";
	private static final String CMD_CONTAR = "CONTAR";
	private static final String CMD_SUMAR= "SUMAR";
	private static final String CMD_FIN = "FIN";

	public String verificar(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERR;
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return ERR;

		String limpio = mensaje.replaceAll("^#|#$", "");
		String partes[] = limpio.split("#");

		String comando = partes[0].trim().toUpperCase();

		if (CMD_FIN.equals(comando)) {
			if (partes.length == 1)
				return FIN;
			else
				return ERR;
		}

		switch (comando) {
		case CMD_LISTANUM:
			if (partes.length == 3)
				return listar(partes[1].trim(), partes[2].trim());
			return ERR;
		case CMD_ABECEDARIO:
			if (partes.length == 3)
				return abecedario(partes[1].trim(), partes[2].trim());
			return ERR;
		case CMD_RANDOM:
			if (partes.length == 3)
				return random(partes[1].trim(), partes[2].trim());
			return ERR;
		case CMD_CONTAR:
			if (partes.length == 2)
				return contar(partes[1].trim());
			return ERR;
		case CMD_SUMAR:
			if (partes.length == 2)
				return sumar(partes[1].trim());
			return ERR;
		default:
			return ERR;
		}

	}

	private static String listar(String iniStr, String finStr) {
		StringBuilder sb = new StringBuilder();
		try {
			int a = Integer.parseInt(iniStr);
			int b = Integer.parseInt(finStr);
			if (a > b)
				return ERR;
			for (int i = a; i <= b; i++) {
				sb.append(i);
				if (i < b)
					sb.append("|");
			}
			return sb.toString();
		} catch (Exception e) {
			return ERR;
		}
	}

	private static String abecedario(String iniStr, String finStr) {
		StringBuilder sb = new StringBuilder();
		try {
			if (iniStr.length() != 1 || finStr.length() != 1)
				return ERR;

			char a = iniStr.charAt(0);
			char b = finStr.charAt(0);
			if (a > b)
				return ERR;

			for (char c = a; c <= b; c++) {
				sb.append(c);
				if (c < b)
					sb.append('-');
			}

			return sb.toString().toUpperCase();
		} catch (Exception e) {
			return ERR;
		}
	}

	private static String random(String iniStr, String finStr) {
		Random random = new Random();
		try {
			int numero = random.nextInt(Integer.parseInt(iniStr), Integer.parseInt(finStr) + 1);
			return String.valueOf(numero);
		} catch (Exception e) {
			return ERR;
		}
	}

	private static String contar(String texto) {

		try {
			return String.valueOf(texto.length());
		} catch (Exception e) {
			return ERR;
		}

	}

	private static String sumar(String numerosStr) {

		// 1, 2, 3, 4,

		try {
			String numeros[] = numerosStr.split(",");
			int suma = 0;
			for (String n : numeros) {
				suma += Integer.parseInt(n);
			}
			return String.valueOf(suma);
		} catch (Exception e) {
			return ERR;
		}

	}

}
