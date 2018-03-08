package taller6;

import java.util.ArrayList;

/**
 * Clase en la cual se implementan los metodos del Taller de Clase #6
 *
 * @author Mauricio Toro, Mateo Agudelo
 */
public class Taller6 {

//    public static int[] cambioGreedy(int n, int[] denominaciones) {
//        return 
//    }
    public static int recorrido(Digraph g) {
        return recorrido(g, new boolean[g.size]);
    }

    public static int recorrido(Digraph g, boolean[] isVisited) {
        int acum = 0;
        int sucesorMenor = 0;
        int i;
        int menor = 0;
        do {
            i = sucesorMenor;
            ArrayList<Integer> sucesores = g.getSuccessors(i);
            isVisited[i] = true;
            acum += menor;
            menor = Integer.MAX_VALUE;
            for (Integer sucesor : sucesores) {
                if (!isVisited[sucesor] && g.getWeight(i, sucesor) < menor) {
                    menor = g.getWeight(i, sucesor);
                    sucesorMenor = sucesor;
                }
            }
        } while (sucesorMenor != i);
        return acum += g.getWeight(i, 0);
    }
}
