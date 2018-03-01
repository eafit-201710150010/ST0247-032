package Taller5;

import static Taller5.Test.add;
import java.util.ArrayList;

/**
 * Clase en la cual se implementan los metodos del Taller de Clase #5
 * 
 * @author Mauricio Toro, Mateo Agudelo
 */
public class Taller5 {

    public static void main(String[] args) {
        DigraphAL g = new DigraphAL(10);
		add(g, 0, 1);
		add(g, 0, 2);
		add(g, 0, 5);
		add(g, 2, 3);
		add(g, 2, 8);
		add(g, 8, 6);
		add(g, 8, 9);
		add(g, 9, 7);
		add(g, 9, 5);
		add(g, 5, 4);
		add(g, 3, 4);
		add(g, 3, 7);
		add(g, 4, 6);
		add(g, 1, 6);
		add(g, 1, 7);
                System.out.println(mColoring(g, 1));
    }
    
    public static boolean mColoring(Digraph g, int m) {
        int [] colors = new int[g.size()];
        return mColoring(g, 0,colors, m);
    }

    private static boolean isSafe(Digraph g, int v, int[] colors, int c) {
        ArrayList<Integer> hijos = g.getSuccessors(v); 
         colors[v]= c;
        for (Integer hijo : hijos) {
            if(colors[hijo] == c){
                return false;
            }
        }
        return true;
    }

    // recomendacion
    private static boolean mColoring(Digraph g, int v, int[] colors, int m) {
        if (v == colors.length) return true;
        for (int i=0; i < m; i++){ 
            if(isSafe(g,colors[i],colors,i)){
              return mColoring(g, v + 1, colors, m);
            }
        }
           return false;
    }

}
