package taller4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase en la cual se implementan los metodos del Taller de Clase #4
 *
 * @author Mauricio Toro, Mateo Agudelo
 */
public class Taller4 {
//
//	public static int recorrido(Digraph g) {
//		 complete...
//	}
//
//	 recomendacion
//	private static int recorrido(Digraph g, int v, int[] unvisited) {
//		 complete...
//	}
//
//	 recomendacion
//	private static int[] removeAt(int k, int a[]) {
//		 complete...
//	}
//
//	public static int costoMinimo(Digraph g, int inicio, int fin) {
//		 complete...
//	}
//
//	 recomendacion
//	private static void dfs(Digraph g, int v, int[] costo) {
//		 complete...
//	}

    public static boolean hayCaminoBfs(Digraph g, int start, int target) {

        return hayCaminoBfs(g, start, target, new boolean[g.size]);
    }

    public static void main(String[] args) {
        DigraphAL g = new DigraphAL(8);
        g.addArc(1, 5, 10);
        g.addArc(0, 1, 20);
        g.addArc(4, 1, 50);
        g.addArc(4, 6, 30);
        g.addArc(0, 6, 90);
        g.addArc(6, 0, 20);
        g.addArc(0, 3, 80);
        g.addArc(5, 3, 40);
        g.addArc(3, 6, 20);
        g.addArc(5, 2, 10);
        g.addArc(2, 5, 50);
        g.addArc(3, 2, 10);
        g.addArc(2, 3, 10);
        g.addArc(2, 7, 20);
        System.out.println(hayCaminoBfs(g, 1, 4));
    }

    private static boolean hayCaminoBfs(Digraph g, int start, int target, boolean[] isVisited) {
        Queue<Integer> cola = new LinkedList();
        cola.add(start);
        isVisited[start] = true;
        ArrayList<Integer> sucesores;
        while (!cola.isEmpty()) {
            sucesores = g.getSuccessors(cola.remove());
            if (sucesores != null) {
                for (Integer sucesor : sucesores) {
                    if (sucesor == target) {
                        return true;
                    }
                    if (!isVisited[sucesor]) {
                        isVisited[sucesor] = true;
                        cola.add(sucesor);
                    }
                }
            }
        }
        return false;
    }
}
