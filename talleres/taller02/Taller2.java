package taller2;

import java.util.ArrayList;
import java.util.Objects;
import static taller2.Taller2.esValido;

/**
 *
 * @author Alejandro Arroyave
 */
public class Taller2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

          System.out.println(queens(4));
          
    }

    //Punto 1
    public static ArrayList<String> combinations(String s) {
        ArrayList<String> list = new ArrayList<>();
        combinations("", s, list);
        System.out.println(list.toString());
        return list;
    }

    private static void combinations(String pre, String pos, ArrayList<String> list) {
        if (pos.length() == 0) {
            list.add(pre);
        } else {
            combinations(pre + pos.substring(0, 1), pos.substring(1), list);
            combinations(pre, pos.substring(1), list);
        }
    }

    //Punto 2
    public static ArrayList<String> permutations(String s) {
        ArrayList<String> list = new ArrayList<>();
        permutations("", s, list);
        System.out.println(list.toString());
        return list;
    }

    private static void permutations(String pre, String pos, ArrayList<String> list) {
        if (pos.length() == 0) {
            list.add(pre);
        } else {
            for (int i = 0; i < pos.length(); i++) {
                permutations(pre + pos.charAt(i), pos.substring(0, i) + pos.substring(i + 1), list);
            }
        }
    }

    public static void imprimirTablero(int[] tablero) {
        int n = tablero.length;
        System.out.print("    ");
        for (int i = 0; i < n; ++i) {
            System.out.print(i + " ");
        }
        System.out.println("\n");
        for (int i = 0; i < n; ++i) {
            System.out.print(i + "   ");
            for (int j = 0; j < n; ++j) {
                System.out.print((tablero[i] == j ? "Q" : "#") + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

 
  
     public static boolean esValido(Integer[] tablero) {
        for (int i = 0; i < tablero.length-1; ++i) {
            for (int j = i +1; j < tablero.length; j++) {
                if (Math.abs(tablero[i]-tablero[j]) == Math.abs(i-j) || Objects.equals(tablero [i], tablero[j]) ) {
                    return false;
                }
            }
        }
        return true;
    }


    private static void generarArreglos(String pre, String rango, ArrayList<ArrayList<Integer>> list) {
        if (rango.length() == 0) {
            ArrayList <Integer> arreglo = new ArrayList<>();
            list.add(arreglo);
            for (int i =0; i < pre.length(); i++){
                arreglo.add(Integer.parseInt(pre.substring(i, i+1)));
            }
        } else {
            for (int i = 0; i < rango.length(); i++) {
                generarArreglos(pre + rango.charAt(i), rango.substring(0,i) + rango.substring(i+1), list);
            }
        }
    }

    public static int queens(int n) {
        String rango = "";
        for (int i = 0; i < n; i++) {
            rango += i;
        }
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        generarArreglos("", rango, list);
        int cont = 0;
        for (ArrayList<Integer> arreglo : list) {        
            if(esValido(arreglo.toArray(new Integer[n]))){
             cont++;   
            }
        }
        return cont;
    }
}
