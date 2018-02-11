/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import lab1.Pair;

/**
 *
 * @author Alejandro Arroyave, Luis Javier Palacio,Santiago Castrillon
 */
public class BicolorableGraph {

    static LinkedList<LinkedList<Pair>> list;
    static int[] arr;

    public static void main(String[] args) {
        while (true) {
            Scanner in = new Scanner(System.in);
            int vertex = in.nextInt();
            if (vertex == 0) {
                break;
            }
            int aristas = in.nextInt();
            BicolorableGraph graph = new BicolorableGraph(vertex);
            arr = new int[list.size()];
            for (int i = 0; i < aristas; i++) {
                int origen = in.nextInt();
                int destino = in.nextInt();
                graph.addArc(origen, destino);
            }
            arr[0] = 1;
            if(graph.Bicolorable(1, 0)){
                System.out.println("NOT BICOLORABLE.");
            } else{
                System.out.println("BICOLORABLE.");
            }
        }
    }

    public BicolorableGraph(int size) {
        list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            LinkedList<Pair> pair = new LinkedList<>();
            list.add(pair);
        }
    }

    public void addArc(int source, int destination) {
        LinkedList<Pair> vertice = list.get(source);
        //para añadir en orden
        int pos = 0;
        for (; pos < vertice.size(); pos++) {
            if (destination < (int) vertice.get(pos).first) {
                break;
            }
        }
        vertice.add(pos, new Pair(destination, 1));
    }

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

    public boolean Bicolorable(int color, int vertex) {
        ArrayList<Integer> vecinos = getSuccessors(vertex);
        if (vecinos == null) {
            return true;
        }
        for (Integer integer : vecinos) {
            if (arr[integer] == color) {
                return false;
            } else if (arr[integer] != 0) {
                return true;
            } else {
                color = color == 1 ? 2 : 1;
                arr[integer] = color;
                return Bicolorable(color, integer);
            }
        }
        return true;
    }
}
