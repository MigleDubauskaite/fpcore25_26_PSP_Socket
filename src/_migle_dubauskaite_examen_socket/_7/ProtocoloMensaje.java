package _migle_dubauskaite_examen_socket._7;

import java.util.Random;

public class ProtocoloMensaje {

	private static final String ERR = "#Error#";
	private static final String FIN = "#Finalizado#";

	private static final String CMD_LISTADO = "Listado números";
	private static final String CMD_RANDOM = "Numero aleatorio";
	private static final String CMD_FIN = "Fin";

	public String verificar(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERR;
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return ERR;

		String contenido = mensaje.substring(1, mensaje.length() - 1);
		String partes[] = contenido.split("#");

		String comando = partes[0];

		switch (comando) {
		case CMD_LISTADO:
			if (partes.length == 3)
				return listar(partes[1], partes[2]);
			else
				return ERR;
		case CMD_RANDOM:
			if (partes.length == 3)
				return numeroRandom(partes[1], partes[2]);
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

	private static String listar(String inicioStr, String finStr) {
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
		} catch (NumberFormatException e) {
			return ERR;
		}
	}

	private static String numeroRandom(String inicioStr, String finStr) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		try {
			int inicio = Integer.parseInt(inicioStr);
			int fin = Integer.parseInt(finStr);
			if (inicio > fin)
				return ERR;
			int numero = 0;

			for (int i = 0; i < 3; i++) {
				numero = random.nextInt(inicio, fin + 1);
				sb.append(numero);
				if (i < 2)
					sb.append("--");
			}

			return sb.toString();
		} catch (NumberFormatException e) {
			return ERR;
		}
	}
}
