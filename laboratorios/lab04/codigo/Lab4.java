/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

import java.util.ArrayList;

/**
 *
 * @author ljpalaciom
 */
public class Lab4 {
   
    static int acum;
    public static int costoTSP(Digraph g) {
        elVecinoMasCercanoParaTSP(g);
        return acum;
    }

    public static ArrayList<Integer> elVecinoMasCercanoParaTSP(Digraph g) {
        int nodoDeposito = 0;
        ArrayList<Integer> lista = new ArrayList<>();
        boolean isVisited[] = new boolean[g.size];

        int actual = nodoDeposito;
        acum = 0;
        lista.add(actual);
        while (true) {
            Pair menor = new Pair(actual, Integer.MAX_VALUE);
            ArrayList<Integer> sucesores = g.getSuccessors(actual);
            isVisited[actual] = true;
            if (sucesores != null) {
                for (Integer sucesor : sucesores) {
                    int pesoActual = g.getWeight(actual, sucesor);
                    if (!isVisited[sucesor] && pesoActual < (int) menor.second) {
                        menor = new Pair(sucesor, pesoActual);
                    }
                }
            }
            if (actual == (int) menor.first) {
                break;
            }
            acum += (int) menor.second;
            actual = (int) menor.first;
            lista.add((int) menor.first);
        }
        acum += g.getWeight(actual, nodoDeposito);
        return lista;
    }
}
