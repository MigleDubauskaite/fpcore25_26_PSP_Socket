package _0_examen._0_con_try_with_resources;

import java.util.random.RandomGenerator;

// LÓGICA DEL JUEGO
// GENERA UN NÚMERO ALEATORIO, COMPRUEBA SI ES CORRECTO
public class JuegoAdivinaNumero {

	private int numeroCorrecto;
	private final static RandomGenerator random = RandomGenerator.getDefault();

	public JuegoAdivinaNumero() {
		numeroCorrecto = random.nextInt(1, 1001);
	}

//	CUANDO EL SERVIDOR RECIBE UN NÚMERO DEL CLIENTE, VERIFICA ESTE NÚMERO
	public String verificarIntento(String intento) {
		try {
			int numeroIntento = Integer.parseInt(intento);
			return numeroIntento == numeroCorrecto ? "Acertado" : "No acertado";
		} catch (Exception e) {
			return "No acertado";
		}
	}

}
