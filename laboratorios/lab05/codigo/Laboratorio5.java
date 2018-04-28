package Lab5;

/**
 * Solución Laboratorio 5 puntos 1.1, 1.2 y 1.3
 * @author Alejandro Arroyave Bedoya, Luis Javier Palacio Mesa, 
 * Santiago Castrillón Galvis
 */
public class Laboratorio5 {
   
    /**
    * Método principal de la clase
    * @param args argumento predeterminado de la clase main
    */ 
    public static void main (String [] args){
        System.out.println("Levenshtein -> " + convert(testLevenshtein()));
        lcs("CGTATGC", "CTGAC");
    }
    
    /**
     * Compara la distancia Levenshtein entre dos cadenas, es decir es número
     * mínimo de modificaciones que se tiene que hacer para que sean iguales
     * @param a Cadena 1 a comparar
     * @param b Cadena 2 a comparar
     * @return Número de modificaciones hecas para que quedaran iguales
     */
    public static int levenshtein(String a, String b) {
        int[][] distan = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            distan[i][0] = i;

        }
        for (int j = 0; j <= b.length(); j++) {
            distan[0][j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int minimo = Math.min(distan[i - 1][j], distan[i][j - 1]); 
                if (minimo < distan[i - 1][j-1]) {
                distan[i][j] = minimo + 1;
                }else{
                    minimo = distan[i - 1][j-1];
                    distan[i][j] = b.charAt(j-1) == a.charAt(i-1) ? minimo : minimo + 1;

                }
            }
        }
         for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                System.out.print(distan[i][j] + " ");
            }
             System.out.println("");
        }
        return distan[a.length()][b.length()];
    }

    /**
     * Encuentra el tamaño de la subsecuencia mas larga presente en ambas 
     * cadenas.
     * @param a Cadena 1 a comparar
     * @param b Cadena 2 a comparar
     * @return El tamaño de la subsecuencia mas larga encontrada en ambas 
     * cadenas.
     */
    public static int lcs(String a, String b) {
        int[][] tabla = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            tabla[i][0] = 0;
        }
        for (int j = 0; j <= b.length(); j++) {
            tabla[0][j] = 0;
        }
        // Voy a hacer ciclos
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i-1) == b.charAt(j-1)) {
                    tabla[i][j] = tabla[i - 1][j - 1] + 1;
                } else // no son iguales
                {
                    
                    tabla[i][j] = Math.max(tabla[i - 1][j],
                            tabla[i][j - 1]);
                }
            }
        }
        int actual = 0;
        String result = "";
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (tabla[i][j] > actual) {
                    result = result + a.charAt(i-1);
                    actual++;
                }
            }
        }
        System.out.println(result);
        return tabla[a.length()][b.length()];
    }
    
    /**
     * Método que prueba con distintas cadenas la distancia Levenshtein
     * @return True si el algoritmo funciona, y false si el algoritmo falla.
     */
    	static boolean testLevenshtein() {
		String[] wordlist = { "hash", "quantum", "fever", "bench", "long", "blade", "object", "orphanage", "flophouse",
				"fathead" };
		int[][] answers = { { 0, 6, 5, 4, 4, 4, 6, 7, 7, 5 }, { 6, 0, 7, 6, 6, 6, 7, 7, 8, 7 },
				{ 5, 7, 0, 4, 5, 5, 5, 9, 8, 5 }, { 4, 6, 4, 0, 4, 4, 4, 8, 8, 7 }, { 4, 6, 5, 4, 0, 4, 6, 7, 7, 7 },
				{ 4, 6, 5, 4, 4, 0, 5, 7, 7, 6 }, { 6, 7, 5, 4, 6, 5, 0, 8, 8, 6 }, { 7, 7, 9, 8, 7, 7, 8, 0, 7, 7 },
				{ 7, 8, 8, 8, 7, 7, 8, 7, 0, 7 }, { 5, 7, 5, 7, 7, 6, 6, 7, 7, 0 } };
		int n = wordlist.length;
		for (int i = 0; i < n; ++i)
			for (int j = 1; j < n; ++j)
				if (levenshtein(wordlist[i], wordlist[j]) != answers[i][j])
					return false;
		return true;
	}

        /**
         * Método que convierte un booleano en lenguaje mas simple de entender.
         * @param b Booleano a transformar
         * @return Si b es true retorna "correcta", de lo contrario retorna
         * "incorrecta"
         */
	static String convert(boolean b) {
		return b ? "correcta" : "incorrecta";
        }
}
