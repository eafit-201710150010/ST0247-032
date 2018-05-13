package ruteovehiculoselectricos;

import java.util.ArrayList;

/**
 * Implementacion de un grafo dirigido usando matrices de adyacencia
 *
 * @author Mauricio Toro, Mateo Agudelo
 */
public class DigraphAM extends Digraph {

    double[][] matrix;

    /**
     * El tamaño del grafo
     * @param size
     */
    public DigraphAM(int size) {
        super(size);
        // complete...
        matrix = new double[size][size];
    }

    /**
     * Este método permite agregar un vértice direccional entre dos nodos de un grafo
     * @param source El nodo fuente 
     * @param destination El nodo destino
     * @param weight La distancia entre los dos nodos
     */
    public void addArc(int source, int destination, double weight) {
        // complete...
        // recuerde: grafo dirigido!
        matrix[source][destination] = weight;
    }

    /**
     * Este método permite obtener los vecinos de un nodo
     * @param vertex El numéro del nodo
     * @return Una lista de enteros que representan los vecinos de un nodo
     */
    public ArrayList<Integer> getSuccessors(int vertex) {
        // complete...
        ArrayList<Integer> s = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            if (matrix[vertex][i] != 0) {
                s.add(i);
            }
        }
        return s.size() == 0 ? null : s;
    }

    /**
     * Permite obtener la distancia entre dos nodos
     * @param source El nodo fuente 
     * @param destination El nodo destino
     * @return
     */
    public double getWeight(int source, int destination) {
        // complete...
        return matrix[source][destination];
    }

}
