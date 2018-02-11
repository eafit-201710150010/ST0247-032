/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjercicioEnLinea;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Alejandro Arroyave, Luis Javier Palacio,Santiago Castrillon
 */
public class BicolorableGraph {

    static LinkedList<LinkedList<Integer>> list;
    static int[] arr;
    static int color = 1;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int vertex = in.nextInt();
            if (vertex == 0) {
                System.out.println();
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
            if (graph.Bicolorable()) {
                System.out.println("BICOLORABLE.");
            } else {
                System.out.println("NOT BICOLORABLE.");
            }
        }
    }

    public BicolorableGraph(int size) {
        list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            LinkedList<Integer> integer = new LinkedList<>();
            list.add(integer);
        }
    }

    public void addArc(int source, int destination) {
        LinkedList<Integer> vertice = list.get(source);
        //para añadir en orden
        int pos = 0;
        for (; pos < vertice.size(); pos++) {
            if (destination < vertice.get(pos)) {
                break;
            }
        }
        vertice.add(pos, destination);
    }

    public ArrayList<Integer> getSuccessors(int vertex) {
        ArrayList<Integer> retornar = new ArrayList<>();

        for (Integer integer : list.get(vertex)) {
            retornar.add(integer);
        }
        return retornar;
    }

    public boolean Bicolorable() {
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Integer> vecinos = getSuccessors(i);
            color = arr[i] == 1 ? 2 : 1;
            for (Integer integer : vecinos) {
                if (arr[integer] == arr[i] && arr[i] != 0) {
                    return false;
                }         
                arr[integer] = color;
            }
        }
        return true;
    }
}
