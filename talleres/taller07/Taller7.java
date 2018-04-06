package taller7;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author ljpalaciom
 */
public class Taller7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Camino");
        Digraph g1 = new DigraphAL(4);
        g1.addArc(0, 1, 7);
        g1.addArc(0, 2, 15);
        g1.addArc(0, 3, 6);
        g1.addArc(1, 0, 2);
        g1.addArc(1, 2, 7);
        g1.addArc(1, 3, 3);
        g1.addArc(2, 0, 9);
        g1.addArc(2, 1, 6);
        g1.addArc(2, 3, 12);
        g1.addArc(3, 0, 10);
        g1.addArc(3, 1, 4);
        g1.addArc(3, 2, 8);
        System.out.println(caminoMinimo(g1, 2, 3));
    }

    public static Pair dijkstra(Digraph g, int inicio) {
        PriorityQueue<Pair<Integer, Integer>> cola = new PriorityQueue<>(new Comparador());
        int padre[] = new int[g.size];
        int dist[] = new int[g.size];
        for (int i = 0; i < g.size; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[inicio] = 0;
        cola.add(new Pair(inicio, 0));
        while (!cola.isEmpty()) {
            int actual = cola.peek().first;
            int pesoActual = cola.poll().second;
            if (pesoActual > dist[actual]) {
                continue;
            }
            ArrayList<Integer> sucesores = g.getSuccessors(actual);
            if(sucesores == null) continue;
            for (Integer sucesor : sucesores) {
                int pesoSucesor = g.getWeight(actual, sucesor);
                if (pesoActual + pesoSucesor < dist[sucesor]) {
                    dist[sucesor] = pesoActual + pesoSucesor;
                    padre[sucesor] = actual;
                    cola.add(new Pair<>(sucesor, dist[sucesor]));
                }
            }
        }
        return new Pair(dist, padre);
    }

    public static int costoMinimo(Digraph g, int inicio, int fin) {
        Pair pair = dijkstra(g, inicio);
        int dist[] = (int[]) pair.first;
        return dist[fin] == Integer.MAX_VALUE ? -1: dist[fin];
    }

    public static ArrayList<Integer> caminoMinimo(Digraph g, int inicio, int fin) {
        if(inicio == fin) return null;
        Pair pair = dijkstra(g, inicio);
        int padre[] = (int[]) pair.second;
        ArrayList<Integer> caminoMinimo = new ArrayList<>();

        while (fin != inicio) {
            caminoMinimo.add(0, fin);
            fin = padre[fin];
        }
        caminoMinimo.add(0,inicio);
        return caminoMinimo;
    }
}
