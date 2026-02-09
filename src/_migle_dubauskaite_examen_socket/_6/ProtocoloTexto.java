package _migle_dubauskaite_examen_socket._6;

import java.util.Random;

public class ProtocoloTexto {

	private final static String ERR = "#Error#";
	private final static String FIN = "#ConexionCerrada#";

	private final static String CMD_LISTA = "LISTANUM";
	private final static String CMD_RANDOM3 = "RANDOM3";
	private final static String CMD_SUMA = "SUMA";
	private final static String CMD_FIN = "FIN";

	public String verificar(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERR;
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return ERR;

		String contenido = mensaje.substring(1, mensaje.length() - 1);
		String partes[] = contenido.split("#");

		String comando = partes[0];

		switch (comando) {
		case CMD_LISTA:
			if (partes.length == 3)
				return listarNum(partes[1], partes[2]);
			else
				return ERR;
		case CMD_RANDOM3:
			if (partes.length == 3)
				return sacar3Random(partes[1], partes[2]);
			else
				return ERR;
		case CMD_SUMA:
			if (partes.length == 2)
				return sumar(partes[1]);
			else
				return ERR;
		case CMD_FIN:
			if (partes.length == 1)
				return FIN;
			else
				return ERR;
		default:
			return ERR;
		}

	}

	private static String listarNum(String inicioStr, String finStr) {
		StringBuilder sb = new StringBuilder();
		try {
			int inicio = Integer.parseInt(inicioStr);
			int fin = Integer.parseInt(finStr);
			if (inicio > fin)
				return ERR;
			for (int i = inicio; i <= fin; i++) {
				sb.append(i);
				if (i < fin)
					sb.append("|");
			}
			return sb.toString();
		} catch (Exception e) {
			return ERR;
		}
	}

	private static String sacar3Random(String minStr, String maxStr) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		try {
			int min = Integer.parseInt(minStr);
			int max = Integer.parseInt(maxStr);
			if (min > max)
				return ERR;

			int numero = 0;
			for (int i = 0; i <= 2; i++) {
				numero = random.nextInt(min, max + 1);
				sb.append(numero);
				if (i < 2)
					sb.append("-");
			}
			return sb.toString();
		} catch (Exception e) {
			return ERR;
		}
	}

	private static String sumar(String numeros) {
		try {
			String nums[] = numeros.split(",");
			int suma = 0;
			for (String n : nums) {
				suma += Integer.parseInt(n);
			}
			return String.valueOf(suma);
		} catch (Exception e) {
			return ERR;
		}
	}

}
