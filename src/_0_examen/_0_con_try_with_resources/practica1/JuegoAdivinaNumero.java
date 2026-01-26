package _0_examen._0_con_try_with_resources.practica1;

import java.util.random.RandomGenerator;

public class JuegoAdivinaNumero {

	private int numeroCorrecto;
	private static RandomGenerator random = RandomGenerator.getDefault();

	public JuegoAdivinaNumero() {
		this.numeroCorrecto = random.nextInt(1, 101);
	}

	// CUANDO EL SERVIDOR RECIBE UN NÚMERO DEL CLIENTE, VERIFICA ESTE NÚMERO
	public String verificar(String intento) {
		try {
			int numeroIntento = Integer.parseInt(intento);
			return numeroIntento == numeroCorrecto ? "Acertado" : "No acertado";
		} catch (Exception e) {
			return "No acertado";
		}
	}

}
