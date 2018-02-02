
package digraph;
import java.util.ArrayList;

/**
 * Implementacion de un grafo dirigido usando matrices de adyacencia
 *
 * @author Mauricio Toro, Mateo Agudelo, Alejandro Arroyave, Santiago Castrillon, Luis Javier Palacio 
 */
public class DigraphAM extends Digraph {
	int [][] matriz;

	public DigraphAM(int size) {
		super(size);
		matriz = new int [size][size];
	}

	public void addArc(int source, int destination, int weight) {
		matriz[source][destination] = weight;
	}

	public ArrayList<Integer> getSuccessors(int vertex) {
		ArrayList<Integer> arraylist = new ArrayList<>();
                for (int i = 0; i < matriz[0].length; i++) {
                    if (matriz[vertex][i]!=0){
                        arraylist.add(i);
                    }
                }
                if (arraylist.isEmpty())
                    return null;
                return arraylist;
	}

	public int getWeight(int source, int destination) {
		return matriz[source][destination];
	}

}
