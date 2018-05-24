package ruteovehiculoselectricos;

import java.util.ArrayList;

/**
 * Clase abstracta para la implementacion de grafos dirigidos
 *
 * @author Mauricio Toro, Mateo Agudelo
 */
public abstract class Digraph {

    /**
     * Tamaño del grafo
     */
    protected int size;

    /**
     *
     * @param size Tamaño del grafo
     */
    public Digraph(int size) {
        this.size = size;
    }

    /**
     * Este método permite agregar un vértice direccional entre dos nodos de un
     * grafo
     *
     * @param source El nodo fuente
     * @param destination El nodo destino
     * @param weight La distancia entre los dos nodos
     */
    public abstract void addArc(int source, int destination, double weight);

    /**
     * Este método permite obtener los vecinos de un nodo
     *
     * @param vertex El numéro del nodo
     * @return Una lista de enteros que representan los vecinos de un nodo
     */
    public abstract ArrayList<Integer> getSuccessors(int vertex);

    /**
     * Permite obtener la distancia entre dos nodos
     *
     * @param source El nodo fuente
     * @param destination El nodo destino
     * @return
     */
    public abstract double getWeight(int source, int destination);

    /**
     * Permite obtener el numero de nodos del grafo
     *
     * @return Un entero que es el tamaño del grafo
     */
    public int size() {
        return size;
    }
}
