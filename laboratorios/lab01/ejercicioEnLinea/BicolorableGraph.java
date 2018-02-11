/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjercicioEnLinea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import lab1.Pair;

/**
 *
 * @author Alejandro Arroyave, Luis Javier Palacio
 */
public class BicolorableGraph {
    
    static LinkedList<LinkedList<Pair>> list;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int vertex = in.nextInt();
        BicolorableGraph graph = new BicolorableGraph(vertex);
        int aristas = in.nextInt();
        for (int i = 0; i < aristas; i++){
            int origen = in.nextInt();
            int destino = in.nextInt();
            graph.addArc (origen,destino);
        }
        System.out.println(graph.Bicolorable());
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
            if(destination < (int) vertice.get(pos).first){
                break;
            }
        }
        vertice.add(pos,new Pair(destination, 1));
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
    
    public boolean Bicolorable (){
        int color = 1; 
        int [] lista = new int [list.size()];
        for (int i=0; i < list.size(); i++){
            if (lista[i] == 0){
                ArrayList <Integer> vecinos = getSuccessors(i);
                for (Integer integer : vecinos) {
                    if (lista[i] != 0 && lista[i] != color ){
                        return false;
                    }else{
                        lista[integer] = color;
                        if (color == 1) lista[i] = 2;
                        else lista[i] = 1;
                    }
                }
            }
        }
        return true;
    }
}
