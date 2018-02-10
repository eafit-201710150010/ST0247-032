/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author ljpalaciom
 */
public class MapGraph {

    private HashMap grafo;

    public MapGraph() {
        grafo = new HashMap();
    }

    public void addArc(long source, long destination, double weight) {
        LinkedList<Pareja> vertice = (LinkedList<Pareja>) grafo.get(source);
        //para añadir en orden
        int pos = 0;
        for (; pos < vertice.size(); pos++) {
            if (destination < (long) vertice.get(pos).vertice) {
                break;
            }
        }
        vertice.add(pos, new Pareja(destination, weight));
    }

    public ArrayList<Long> getSuccessors(long vertex) {
        ArrayList<Long> retornar = new ArrayList<>();

        for (Pareja pareja : (LinkedList<Pareja>) grafo.get(vertex)) {
            retornar.add(pareja.vertice);
        }
        if (retornar.isEmpty()) {
            return null;
        }
        return retornar;
    }

    public Double getWeight(long source, long destination) {
        ArrayList<Long> retornar = new ArrayList<>();
        for (Pareja pareja : (LinkedList<Pareja>) grafo.get(source)) {
            if ( pareja.vertice == destination) {
                return  pareja.peso;
            }
        }
        return 0.;
    }

    public void addVertex(long vertex) {
        LinkedList<Pareja> lista = new LinkedList<>();
        grafo.put(vertex, lista);
    }
}
