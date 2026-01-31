package _0_examen._01_procesador_mensajes.mi_intento_1;

import java.util.Random;

public class JuegoInstrucciones {

	private static String RESPONSE_ERROR = "#Error#";
	private static String RESPONSE_FINALIZADO = "#Finalizado#";

	private static String CMD_LISTADO = "Listado números";
	private static String CMD_RANDOM = "Número aleatorio";
	private static String CMD_FIN = "Fin";

	public String procesarInstrucciones(String mensajeCliente) {

		if (!mensajeCliente.endsWith("#") || !mensajeCliente.startsWith("#"))
			return RESPONSE_ERROR;

		String contenidoLimpio = mensajeCliente.substring(1, mensajeCliente.length() - 1);
		String partes[] = contenidoLimpio.split("#");

		String instruccion = partes[0];

		if (CMD_FIN.equals(instruccion))
			return RESPONSE_FINALIZADO;
		if (!mensajeValido(partes))
			return RESPONSE_ERROR;

		int inicio, fin;
		try {
			inicio = Integer.parseInt(partes[1]);
			fin = Integer.parseInt(partes[2]);
		} catch (NumberFormatException e) {
			return RESPONSE_ERROR;
		}

		if (inicio > fin)
			return RESPONSE_ERROR;

		if (CMD_LISTADO.equals(instruccion)) {
			return generarNumeros(inicio, fin);
		}

		if (CMD_RANDOM.equals(instruccion))
			return generarNumeroAleatorio(inicio, fin);

		return RESPONSE_ERROR;
	}

	private static boolean mensajeValido(String partes[]) {
		return partes.length == 3;
	}

	private static String generarNumeros(int inicio, int fin) {
		StringBuilder sb = new StringBuilder();

		for (int i = inicio; i <= fin; i++) {
			sb.append(i);
			if (i < fin)
				sb.append("|");
		}
		return sb.toString();
	}

	private static String generarNumeroAleatorio(int entre1, int entre2) {
		Random random = new Random();
		int numeroRandom = random.nextInt(entre1, entre2 + 1);
		return String.valueOf(numeroRandom);
	}

}
