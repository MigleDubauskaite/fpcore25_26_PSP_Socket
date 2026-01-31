package _0_examen._01_procesador_mensajes;

public class Juego {

	// CONSTANTES DE COMANDOS
	private static final String CMD_LISTADO = "Listado números";
	private static final String CMD_ALEATORIO = "Numero aleatorio";
	private static final String CMD_FIN = "Fin";

	private static final String RESP_ERROR = "#Error#";
	private static final String RESP_FINALIZADO = "#Finalizado#";

	public String verificarMensajesValidos(String mensaje) {

		// Validación básica de formato
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#")) {
			return RESP_ERROR;
		}

		// Eliminar # inicial y final
		String contenido = mensaje.substring(1, mensaje.length() - 1);
		String[] partes = contenido.split("#");

		String comando = partes[0];

		// COMANDO FIN
		if (CMD_FIN.equals(comando)) {
			if (partes.length != 1) {
				return RESP_ERROR;
			}
			return RESP_FINALIZADO;
		}

		// Validación común para comandos con parámetros
		if (!esMensajeValido(partes)) {
			return RESP_ERROR;
		}

		int inicio, fin;
		try {
			inicio = Integer.parseInt(partes[1]);
			fin = Integer.parseInt(partes[2]);
		} catch (NumberFormatException e) {
			return RESP_ERROR;
		}

		if (inicio > fin) {
			return RESP_ERROR;
		}

		// LISTADO DE NÚMEROS
		if (CMD_LISTADO.equals(comando)) {
			return generarListado(inicio, fin);
		}

		// NÚMERO ALEATORIO
		if (CMD_ALEATORIO.equals(comando)) {
			return generarNumeroAleatorio(inicio, fin);
		}

		return RESP_ERROR;
	}

	// ---------- MÉTODOS AUXILIARES ----------

	private boolean esMensajeValido(String[] partes) {
		return partes.length == 3;
	}

	private String generarListado(int inicio, int fin) {
		StringBuilder sb = new StringBuilder();
		for (int i = inicio; i <= fin; i++) {
			sb.append(i);
			if (i < fin) {
				sb.append("|");
			}
		}
		return sb.toString();
	}

	private String generarNumeroAleatorio(int inicio, int fin) {
		int aleatorio = inicio + (int) (Math.random() * (fin - inicio + 1));
		return String.valueOf(aleatorio);
	}
}
