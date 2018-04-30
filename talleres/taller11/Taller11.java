package taller11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Clase en la cual se implementan los metodos del Taller de Clase #11
 *
 * @author Mauricio Toro, Mateo Agudelo
 */
public class Taller11 {
      
    public static int heldKarp(Digraph g) {
        int n = g.size;
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
    
}
