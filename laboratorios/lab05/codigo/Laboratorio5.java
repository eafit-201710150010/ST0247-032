package Lab5;

/**
 * Solución Laboratorio 5 puntos 1.1, 1.2 y 1.3
 * @author Alejandro Arroyave Bedoya, Luis Javier Palacio Mesa, 
 * Santiago Castrillón Galvis
 */
public class Laboratorio5 {
  
 /**
 * Una implementacion del algoritmo de heldKarp que permite resolver de manera optima el problema de tsp
 * @param g El grafo a analizar
 * @return El costo minimo del tsp
 */
    public static int heldKarp(Digraph g) {
        int n = g.size();
        int numSub = (int) Math.pow(2, n - 1);
        int pesos[][] = new int[n][numSub];
        LinkedList<BitmaskSet> subconjuntos = new LinkedList<>();

        combinations(n - 1, subconjuntos, new ArrayList());

        for (BitmaskSet sub1 : subconjuntos) {
            for (int i = n - 1; i >= 0; --i) {
                int min = Integer.MAX_VALUE;
                int porDonde = 1;
                for (BitmaskSet sub2 : subconjuntos) {
                    if (sub1.length() <= sub2.length()) {
                        continue;
                    }
                    int acum = 0;
                    for (int k = 1; k < n; k++) {

                        if (sub1.contains(k) && sub2.contains(k)) {
                            acum++;
                        } else if (sub1.contains(k)) {
                            porDonde = k;
                        }
                    }
                    if (acum == sub1.length() - 1) {
                        int valor = pesos[porDonde][sub2.mask() / 2] + g.getWeight(porDonde, i);

                        if (valor < min) {
                            min = valor;
                        }
                    }
                }
                if (min == Integer.MAX_VALUE) {
                    pesos[i][sub1.mask() / 2] = g.getWeight(0, i);
                } else {
                    pesos[i][sub1.mask() / 2] = min;
                }
            }
        }
        return pesos[0][numSub - 1];
    }
    /**
     * Un algoritmo que obtiene un conjunto potencia de n P(A) donde A ={1,2,3...n} 
     * @param n Es el último elemento del conjunto A
     * @param subconjuntos En esta lista enlazada se ira agregando en orden de tamano los subconjuntos
     * @param actual Es una variable que permite moldear cada subconjunto
     */
    public static void combinations(int n, LinkedList<BitmaskSet> subconjuntos, ArrayList<Integer> actual) {
        if (n == 0) {
            BitmaskSet temp = new BitmaskSet();
            int size = 0;
            for (Integer integer : actual) {
                temp.add(integer);
            }
            int pos = 0;
            for (; pos < subconjuntos.size(); pos++) {
                if (actual.size() < (int) subconjuntos.get(pos).length()) {
                    break;
                }
            }
            subconjuntos.add(pos, temp);
        } else {
            actual.add(n);
            combinations(n - 1, subconjuntos, actual);
            actual.remove(actual.size() - 1);
            combinations(n - 1, subconjuntos, actual);
        }
    }
	
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
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    tabla[i][j] = tabla[i - 1][j - 1] + 1;
                } else {
                    tabla[i][j] = Math.max(tabla[i - 1][j],
                            tabla[i][j - 1]);
                }
            }
        }
        return tabla[a.length()][b.length()];
    }
    
    /**
     * Encuentra la subsecuencia mas larga presente en ambas cadenas.
     * @param a Cadena 1 a comparar
     * @param b Cadena 2 a comparar
     * @return La subsecuencia mas larga encontrada en ambas cadenas.
     */
    public static String lcsCadena(String a, String b) {
        int[][] tabla = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    tabla[i][j] = tabla[i - 1][j - 1] + 1;
                } else {
                    tabla[i][j] = Math.max(tabla[i - 1][j],
                            tabla[i][j - 1]);
                }
            }
        }

        StringBuilder result = new StringBuilder("");
        int i = a.length(), j = b.length();
        while (result.length() != tabla[a.length()][b.length()]) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                result.insert(0, a.charAt(i - 1));
                i--;
                j--;
            } else if (tabla[i - 1][j] > tabla[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return result.toString();
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
