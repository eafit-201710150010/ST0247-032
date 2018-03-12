package lab3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author ljpalaciom
 */
public class EjercicioEnLinea {

    static Pair ruta;

    //Punto 2.1

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        try {
            String lineaPartida[] = lector.readLine().split(" ");
            int n = Integer.parseInt(lineaPartida[0]);
            int m = Integer.parseInt(lineaPartida[1]);
            DigraphAL grafo = new DigraphAL(n + 1);
            while (m > 0) {
                lineaPartida = lector.readLine().split(" ");
                grafo.addArc(Integer.parseInt(lineaPartida[0]), Integer.parseInt(lineaPartida[1]), Integer.parseInt(lineaPartida[2]));
                m--;
            }

            for (Integer actual : costoMinimo(grafo, 1, n)) {
                System.out.print(actual + " ");
            }
        } catch (Exception e) {
            System.out.println("No puede seeeeeeeeer, israel israel");
        }
    }

    /**
     *
     * @param g
     * @param inicio
     * @param fin
     * @return
     */
    public static ArrayList<Integer> costoMinimo(Digraph g, int inicio, int fin) {
        int costo[] = new int[g.size];
        for (int i = 0; i < costo.length; i++) {
            costo[i] = Integer.MAX_VALUE;
        }
        costo[inicio] = 0;
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(inicio);
        dfs(g, inicio, fin, costo, lista, 0);
        return (ArrayList<Integer>) ruta.first;
    }

    private static void dfs(Digraph g, int nodo, int objetivo, int[] costo, ArrayList<Integer> list, int acum) {
        ArrayList<Integer> sucesores = g.getSuccessors(nodo);
        if (nodo == objetivo) {
            Pair pair = new Pair(list.clone(), acum);
            if (ruta == null) {
                ruta = pair;
            } else if ((int) pair.second < (int) ruta.second) {
                ruta = pair;
            }
            return;
        }
        if (sucesores != null) {
            for (Integer sucesor : sucesores) {
                int peso = g.getWeight(nodo, sucesor);
                if (costo[sucesor] > acum + peso) {
                    list.add(sucesor);
                    costo[sucesor] = acum + peso;
                    dfs(g, sucesor, objetivo, costo, list, acum + peso);
                    list.remove(list.size() - 1);
                }
            }
        }
    }
}
