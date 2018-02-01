package digraph;

import java.util.ArrayList;

/**
 * Implementacion de un grafo dirigido usando listas de adyacencia
 *
 * @author Mauricio Toro, Mateo Agudelo, <su nombre>
 */
public class DigraphAL extends Digraph {

    ArrayList<ArrayList<Pair>> arraylist;

    public DigraphAL(int size) {
        super(size);
        arraylist = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Pair> pair = new ArrayList<>();
            arraylist.add(pair);
        }
    }

    public void addArc(int source, int destination, int weight) {
        for (Pair pair : arraylist.get(source)) {
            if(destination > (int)pair.first){
               arraylist.get(source).add(new Pair(destination, weight));
            }
        }
       
    }

    public ArrayList<Integer> getSuccessors(int vertex) {
        ArrayList<Integer> retornar = new ArrayList<>();

        for (Pair pair : arraylist.get(vertex)) {
            retornar.add((int)pair.first);
        }
        if (retornar.isEmpty()) {
            return null;
        }
        return retornar;
    }

    public int getWeight(int source, int destination) {
        ArrayList<Integer> retornar = new ArrayList<>();
        for (Pair pair : arraylist.get(source)) {
            if ((int)pair.first == destination) {
                return (int) pair.second;
            }
        }
        return 0;
    }
}
