package _0_examen._01_procesador_mensajes.practica3_otro;

public class InterpreteInstrucciones {
	
	private static final String ERR = "#ERROR#";
	private static final String FIN = "#OK#";
	
	private static final String CMD_RANGO = "RANGO";
	private static final String CMD_ALEATORIO = "ALEATORIO";
	private static final String CMD_CONTAR = "CONTAR";
	private static final String CMD_FIN = "FIN";
	
	public String verificarMensajes(String mensaje) {
		
		if(mensaje == null || mensaje.isBlank()) return ERR;
		if(!mensaje.startsWith("#") || !mensaje.endsWith("#")) return ERR;
		
		mensaje = mensaje.trim();
		
		String limpio = mensaje.substring(1, mensaje.length() - 1);
		String partes[] = limpio.split("#", -1);
		
		
	    String comando = partes[0].trim();

	    if(comando.equals(CMD_FIN) && partes.length==1) return FIN;
	    
	    if (partes.length == 3) {
	        String arg1 = partes[1];
	        String arg2 = partes[2];

	        switch (comando) {
	            case CMD_RANGO:
	                return devolverNumeros(arg1, arg2);
	            default:
	                return ERR;
	        }
	    }
	    
	    return ERR;
	}
	
	private static String devolverNumeros(String inicio, String fin) {
		StringBuilder sb = new StringBuilder();
		int a = 0;
		int b = 0;
		
		try {
			a = Integer.parseInt(inicio.trim());
			b = Integer.parseInt(fin.trim());
		} catch (NumberFormatException e) {
			return ERR;
		}
		
		if(a > b) return ERR;
		
		for (int i = a; i <= b; i++) {
			sb.append(i);
			if(i<b) sb.append(",");
		}
		
		return sb.toString();
	} 
	

}
