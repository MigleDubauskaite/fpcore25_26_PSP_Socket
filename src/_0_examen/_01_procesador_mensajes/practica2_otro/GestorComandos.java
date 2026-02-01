package _0_examen._01_procesador_mensajes.practica2_otro;

public class GestorComandos {

	private static String ERROR = "ERROR";
	private static final String FIN = "FIN";

	private static final String REPETIR = "REPETIR";
	private static final String CONTAR = "CONTAR";
	private static final String MAYUSCULAS = "MAYUSCULAS";
	private static final String SALIR = "SALIR";

	public String gestionarComandos(String mensaje) {

		if (mensaje == null || mensaje.isBlank())
			return ERROR;

		mensaje = mensaje.toUpperCase();

		String partes[] = mensaje.split("\\|"); // Error clásico: "|" es una expresión regular especial. Eso parte carácter a carácter.

		if (!mensajeValido(partes))
			return ERROR;

		String comando = partes[0];
		String argumento1 = partes[1];
		String argumento2 = partes[2];
		// String argumento3 = partes[3];

		switch (comando) {
		case REPETIR:
			return mensajeRepetido(argumento1, argumento2);
		case CONTAR:
			return contarLetras(argumento1, argumento2);
		case MAYUSCULAS:
			return convertirMayus(argumento1);
		case SALIR:
			return FIN;
		default:
			return ERROR;
		}
	}

	private static boolean mensajeValido(String[] partes) {
		return partes.length == 4;
	}

	private static String mensajeRepetido(String texto, String vecesS) {
		if(texto==null||texto.isBlank()) return ERROR;
		
		int veces;
		try {
		    veces = Integer.parseInt(vecesS);
		} catch (NumberFormatException e) {
		    return ERROR;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < veces; i++) {
			sb.append(texto + " ");
		}
		return sb.toString();
	}

	private static String contarLetras(String texto, String caracterS) {
		if(texto==null) return "...";
		if (texto == null || caracterS == null || caracterS.length() != 1)
            return ERROR;
		int contador = 0;
		char ch = caracterS.charAt(0);

		for (char c : texto.toCharArray()) {
			if (ch == c)
				contador++;
		}
		return String.valueOf(contador);
	}

	private static String convertirMayus(String texto) {
		return texto.toUpperCase();
	}

}
