package _0_examen._01;

public class Juego {

	public String verificarMensajesValidos(String mensaje) {

		int inicio;
		int fin;

//		Validamos si mensaje empieza y termina con #
		if (!mensaje.startsWith("#") || !mensaje.endsWith("#"))
			return "#Error#";

//		Quitar '#' del inicio y final 
		String contenidoModificado = mensaje.substring(1, mensaje.length() - 1); // Resultado: Listado números#2#11

//		Separar por '#' 
		String[] partesMensajes = contenidoModificado.split("#"); // Resultado: ["Listado números", "2", "11"]

//		La primera parte del array es el comando enviado por cliente
		String mensajeEnviadoPorCliente = partesMensajes[0]; // posibles comandos: Listado números, Número aleatorio, Fin

//		Si el cliente envía Fin
		if (mensajeEnviadoPorCliente.equals("Fin")) {
			if (partesMensajes.length != 1) {
				return "#Error#";
			}
			return "#Finalizado#";
		} else if (partesMensajes.length != 3) { // otros comandos tiene que tener 3 partes
			return "#Error#";
		}

		// nos aseguramos que los números enviados sean enteros y no letras
		try {
			inicio = Integer.parseInt(partesMensajes[1]);
			fin = Integer.parseInt(partesMensajes[2]);
		} catch (Exception e) {
			return "#Error#";
		}

		// mensaje a devolver
		if (mensajeEnviadoPorCliente.equals("Listado números")) {
			StringBuilder sb = new StringBuilder();

			for (int i = inicio; i < fin; i++) {
				sb.append(i);
				if (i <= fin)
					sb.append("|");
			}
			return sb.toString();
		}
		return "#Error#";
	}

	public static void main(String[] args) {

		Juego juego = new Juego();

		System.out.println(juego.verificarMensajesValidos("#Listado números#2#11#"));

	}

}
