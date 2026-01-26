package _0_examen._0_sin_try_with_resources;

import java.util.random.RandomGenerator;

/**
 * La clase JuegoAdivinaNumero implementa la lógica para un juego simple donde
 * se debe adivinar un número generado aleatoriamente.
 */

public class JuegoAdivinaNumero {

	private int numeroEscondido;
	private final static RandomGenerator random = RandomGenerator.getDefault();

	public JuegoAdivinaNumero() {
		numeroEscondido = random.nextInt(1, 11);
	}

	/**
	 * Verifica si el intento del jugador es igual al número generado
	 * aleatoriamente.
	 * 
	 * @param intento El intento del jugador como un String.
	 * @return "Acertado" si el intento coincide con el número escondido, "No
	 *         acertado" en caso contrario. Si el intento no es un número válido,
	 *         también devuelve "No acertado".
	 */

	public String verificarIntento(String intento) {
		try {
			int numeroIntento = Integer.parseInt(intento);
			return numeroIntento == numeroEscondido ? "Acertado" : "No acertado";
		} catch (Exception e) {
			return "No acertado";
		}
	}

}
