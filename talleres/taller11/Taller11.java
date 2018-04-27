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

        combinations(n, subconjuntos, new ArrayList());
        for (int i = 0; i < n; ++i) {
            for (BitmaskSet sub1: subconjuntos) {
                int min = Integer.MAX_VALUE;
                String binary = Integer.toBinaryString(sub1.mask());
                for (BitmaskSet sub2 : subconjuntos) {
                    String actual = Integer.toBinaryString(sub2.mask());
                    int acum = 0;
                    for (int k = binary.length() - 1, j = actual.length() - 1; k >= 0 && j >= 0; ++k, ++j) {
                        if (binary.charAt(k) == '1' && actual.charAt(j) == '1') {
                            acum++;
                        }
                    }
                    if (acum == sub1.length() -1) {
                    }
                }

            }
        }
        return 0;
    }

    public static void main(String[] args) {
        LinkedList<BitmaskSet> subconjuntos = new LinkedList<>();
        combinations(3, subconjuntos, new ArrayList());
        for (BitmaskSet subconjunto : subconjuntos) {
            System.out.println(Integer.toBinaryString(subconjunto.mask()).length());
        }
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
