package _migle_dubauskaite_examen_socket._5;

import java.util.Random;

public class Protocolo {

	private static final String ERROR = "#Error#";
	private static final String FIN = "#FIN#";
	private static final String CERRADA = "#ConexionCerrada#";

	private Random random = new Random();

	public String verificar(String mensaje) {

		if (mensaje == null || mensaje.isBlank()) {
			return ERROR;
		}

		// FIN
		if (FIN.equals(mensaje)) {
			return CERRADA;
		}

		// Eliminamos el primer y último #
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#")) {
			return ERROR;
		}

		String[] partes = mensaje.substring(1, mensaje.length() - 1).split("#");

		try {
			switch (partes[0]) {

			case "LISTANUM":
				return listarNumeros(partes);

			case "ABECEDARIO":
				return listarAbecedario(partes);

			case "RANDOMN":
				return randomN(partes);

			case "CONTAR":
				return contarCaracteres(partes);

			case "MAYUS":
				return partes[1].toUpperCase();

			default:
				return ERROR;
			}

		} catch (Exception e) {
			return ERROR;
		}
	}

	// ---------- MÉTODOS AUXILIARES ----------

	private String listarNumeros(String[] partes) {
		if (partes.length != 3)
			return ERROR;

		int inicio = Integer.parseInt(partes[1]);
		int fin = Integer.parseInt(partes[2]);

		if (inicio > fin)
			return ERROR;

		StringBuilder sb = new StringBuilder();
		for (int i = inicio; i <= fin; i++) {
			sb.append(i).append("|");
		}

		return sb.substring(0, sb.length() - 1);
	}

	private String listarAbecedario(String[] partes) {
		if (partes.length != 3)
			return ERROR;

		char inicio = partes[1].toUpperCase().charAt(0);
		char fin = partes[2].toUpperCase().charAt(0);

		if (inicio > fin)
			return ERROR;

		StringBuilder sb = new StringBuilder();
		for (char c = inicio; c <= fin; c++) {
			sb.append(c).append("-");
		}

		return sb.substring(0, sb.length() - 1);
	}

	private String randomN(String[] partes) {
		if (partes.length != 4)
			return ERROR;

		int min = Integer.parseInt(partes[1]);
		int max = Integer.parseInt(partes[2]);
		int cantidad = Integer.parseInt(partes[3]);

		if (min > max || cantidad <= 0)
			return ERROR;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cantidad; i++) {
			int num = random.nextInt(max - min + 1) + min;
			sb.append(num).append("|");
		}

		return sb.substring(0, sb.length() - 1);
	}

	private String contarCaracteres(String[] partes) {
		if (partes.length != 2)
			return ERROR;
		return String.valueOf(partes[1].length());
	}
}
