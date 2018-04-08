package lab4;
import java.util.ArrayList;

/**
 * Clase abstracta para la implementacion de grafos dirigidos
 *
 * @author Mauricio Toro, Mateo Agudelo
 */
public abstract class Digraph {

    /**
     *
     */
    protected int size;

    /**
     *
     * @param vertices
     */
    public Digraph(int vertices) {
		size = vertices;
	}

    /**
     *
     * @param source
     * @param destination
     * @param weight
     */
    public abstract void addArc(int source, int destination, int weight);

    /**
     *
     * @param vertex
     * @return
     */
    public abstract ArrayList<Integer> getSuccessors(int vertex);

    /**
     *
     * @param source
     * @param destination
     * @return
     */
    public abstract int getWeight(int source, int destination);

    /**
     *
     * @return
     */
    public int size() {
		return size;
	}
}
