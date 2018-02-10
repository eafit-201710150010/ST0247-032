package lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains algorithms for digraphs Adapted from:
 * http://cs.fit.edu/~ryan/java/programs/graph/Dijkstra-java.html
 *
 * @author Mauricio Toro
 * @version 1
 */
public class DigraphAlgorithms {
    static MapGraph graph;
    private static int minVertex(int[] dist, boolean[] v) {
        int x = Integer.MAX_VALUE;
        int y = -1;   // graph not connected, or no unvisited vertices
        for (int i = 0; i < dist.length; i++) {
            if (!v[i] && dist[i] < x) {
                y = i;
                x = dist[i];
            }
        }
        return y;
    }

    static int[] dijsktra(Digraph dg, int source) {
        final int[] dist = new int[dg.size()];  // shortest known distance from "s"
        final int[] pred = new int[dg.size()];  // preceeding node in path
        final boolean[] visited = new boolean[dg.size()]; // all false initially

        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE; //Infinity
        }
        dist[source] = 0;

        for (int i = 0; i < dist.length; i++) {
            final int next = minVertex(dist, visited);
            visited[next] = true;

            // The shortest path to next is dist[next] and via pred[next].
            final ArrayList<Integer> n = dg.getSuccessors(next);
            for (int j = 0; j < n.size(); j++) {
                final int v = n.get(j);
                final int d = dist[next] + dg.getWeight(next, v);
                if (dist[v] > d) {
                    dist[v] = d;
                    pred[v] = next;
                }
            }
        }
        return pred;  // (ignore pred[s]==0!)
    }

    public static ArrayList getPath(int[] pred, int s, int e) {
        final java.util.ArrayList path = new java.util.ArrayList();
        int x = e;
        while (x != s) {
            path.add(0, x);
            x = pred[x];
        }
        path.add(0, s);
        return path;
    }

    // Código para dibujar el grafo en GraphViz
    // Recomiendo www.webgraphviz.com/
    public static void dibujarGrafo(Digraph g) {
        System.out.println("digraph Grafo {");
        System.out.println("node [color=cyan, style=filled];");
        int nv = g.size();
        for (int i = 0; i < nv; i++) {
            ArrayList<Integer> lista = g.getSuccessors(i);
            for (int j = 0; j < lista.size(); j++) {
                System.out.println("\"" + i + "\" -> \"" + lista.get(j) + "\" [ label=\"" + g.getWeight(i, lista.get(j)) + "\"];");
            }
        }
        System.out.println("}");
    }

    public static int maxVertex(Digraph graph) {
        int max = 0;
        int vertex = 0;
        for (int i = 0; i < graph.size; i++) {
            if (max < graph.getSuccessors(i).size()) {
                max = graph.getSuccessors(i).size();
                vertex = i;
            }
        }
        return vertex;
    }

    public static void main(String[] args) {
        DigraphAL dgal = new DigraphAL(5);
        dgal.addArc(0, 1, 10);
        dgal.addArc(0, 2, 3);
        dgal.addArc(1, 2, 1);
        dgal.addArc(1, 3, 2);
        dgal.addArc(2, 1, 4);
        dgal.addArc(2, 3, 8);
        dgal.addArc(2, 4, 2);
        dgal.addArc(3, 4, 7);
        dgal.addArc(4, 3, 9);
        //1.1 1.2 1.3
        System.out.println(getPath(dijsktra(dgal, 0), 0, 3));
        System.out.println("Vertex with more neighbors in the DigraphAl " + maxVertex(dgal));

        DigraphAM dgam = new DigraphAM(5);
        dgam.addArc(0, 1, 10);
        dgam.addArc(0, 2, 3);
        dgam.addArc(1, 2, 1);
        dgam.addArc(1, 3, 2);
        dgam.addArc(2, 1, 4);
        dgam.addArc(2, 3, 8);
        dgam.addArc(2, 4, 2);
        dgam.addArc(3, 4, 7);
        dgam.addArc(4, 3, 9);

        System.out.println(getPath(dijsktra(dgam, 0), 0, 3));
        System.out.println("Vertex with more neighbors in the DigraphAM " + maxVertex(dgam));
        DigraphAlgorithms.dibujarGrafo(dgal);
        leerArchivo();
        Long origen = new Long("3474224757");
        Long destino = new Long("287291920");
        System.out.println(graph.getWeight(origen,destino));//Hermoso
    }

    public static void leerArchivo() {
        try {
            BufferedReader archivo = new BufferedReader(new FileReader("medellin_colombia-grande.txt"));
            String linea;
            graph = new MapGraph();
            archivo.readLine();
            while ((linea = archivo.readLine()) != null) {
                Scanner leerLinea = new Scanner(linea);
                if (leerLinea.hasNextLong()) {
                    long vertex = leerLinea.nextLong();
                    graph.addVertex(vertex);
                } else {
                    break;
                }
            }
            archivo.readLine();
            while ((linea = archivo.readLine()) != null) {
                Scanner leerLinea = new Scanner(linea);
                if (leerLinea.hasNext()) {
                    long origen = leerLinea.nextLong(); 
                    long destino = leerLinea.nextLong();
                    String linea1 = leerLinea.next();      
                    graph.addArc(origen, destino, Double.parseDouble(linea1));
                } else {
                    archivo.close();
                    leerLinea.close();
                    break;   
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
