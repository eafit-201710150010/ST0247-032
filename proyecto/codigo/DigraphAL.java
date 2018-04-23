package ruteovehiculoselectricos;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implementacion de un grafo dirigido usando listas de adyacencia
 *
 * @author Mauricio Toro, Mateo Agudelo, <su nombre>
 */
public class DigraphAL extends Digraph {
	// complete...
	ArrayList<ArrayList<Pair<Integer, Double>>> list;
        
	public DigraphAL(int size) {
		super(size);
		// complete...
		list = new ArrayList<>(size);
		for (int i = 0; i < size; ++i)
			list.add(i, new ArrayList<>());
	}

	public void addArc(int source, int destination, double weight) {
		list.get(source).add(Pair.makePair(destination, weight));
	}

        @Override
	public ArrayList<Integer> getSuccessors(int vertex) {
		// complete...
		ArrayList<Integer> s = new ArrayList<>(list.get(vertex).size());
		for (Pair<Integer, Double> p : list.get(vertex))
			s.add(p.first);
		if (s.isEmpty())
			return null;
		return s;
	}

        @Override
	public double getWeight(int source, int destination) {
		// complete...
		for (Pair<Integer, Double> p : list.get(source))
			if (p.first == destination)
				return p.second;
		return 0;
	}
}
