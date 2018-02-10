package lab1;

import java.util.*;

/**
 * Implementacion de un grafo dirigido usando listas de adyacencia
 *
 * @author Mauricio Toro, Mateo Agudelo, Alejandro Arroyave, Santiago Castrillon, Luis Javier Palacio
 */
public class DigraphAL extends Digraph {

    LinkedList<LinkedList<Pair>> list;

    public DigraphAL(int size) {
        super(size);
        list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            LinkedList<Pair> pair = new LinkedList<>();
            list.add(pair);
        }
    }

    @Override
    public void addArc(int source, int destination, int weight) {
        LinkedList<Pair> vertice = list.get(source);
	//para añadir en orden
	int pos = 0;
        for (; pos < vertice.size(); pos++) {
            if(destination < (int) vertice.get(pos).first){
                break;
            }
        }
        vertice.add(pos,new Pair(destination, weight));
    }

    @Override
    public ArrayList<Integer> getSuccessors(int vertex) {
        ArrayList<Integer> retornar = new ArrayList<>();

        for (Pair pair : list.get(vertex)) {
            retornar.add((int) pair.first);
        }
        if (retornar.isEmpty()) {
            return null;
        }
        return retornar;
    }

    public int getWeight(int source, int destination) {
        ArrayList<Integer> retornar = new ArrayList<>();
        for (Pair pair : list.get(source)) {
            if ((int) pair.first == destination) {
                return (int) pair.second;
            }
        }
        return 0;
    }
}
