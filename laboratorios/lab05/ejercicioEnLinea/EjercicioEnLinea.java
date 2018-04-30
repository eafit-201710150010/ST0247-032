package EjercicioEnLinea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import lab5.*;

/**
 *
 * @author ljpalaciom
 */
public class EjercicioEnLinea {
    static int menor;
   
    public static int recorrido(Digraph g) {
        int arr[] = new int[g.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        menor = peso(g, arr);
        recorrido(g, arr, 0);
        return menor;
    }

    private static void cambiar(int[] arr, int i, int k) {
        int t = arr[i];
        arr[i] = arr[k];
        arr[k] = t;
    }

    private static int peso(Digraph g, int[] arr) {
        int acum = 0;
        for (int i = 0; i < g.size() - 1; i++) {
            acum += g.getWeight(arr[i], arr[i + 1]);
        }
        return acum += g.getWeight(arr[arr.length - 1], arr[0]);
    }

     private static void recorrido(Digraph g, int[] arr, int start) {
        for (int i = start; i < arr.length; i++) {
            cambiar(arr, i, start);
            int peso = peso(g, arr);
            if (peso < menor) {
                menor = peso;
            }
            recorrido(g, arr, start + 1);
            cambiar(arr, start, i);
        }
    }

    public static void main(String[] args) {
        try {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        String linea;
        String lineaPartida[];
        Pair<Integer, Integer>[] coordenadas;
        
            linea = lector.readLine();

            int t = Integer.parseInt(linea);
            for (int i = 0; i < t; i++) {

                lector.readLine();
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                Pair<Integer, Integer> posicionInicial = new Pair(Integer.parseInt(lineaPartida[0]), Integer.parseInt(lineaPartida[1]));
                linea = lector.readLine();
                int numDesechos = Integer.parseInt(linea);
                DigraphAM grafo = new DigraphAM(numDesechos + 1);
                coordenadas = new Pair[numDesechos + 1];
                coordenadas[0] = posicionInicial;
                for (int j = 1; j <= numDesechos; j++) {
                    linea = lector.readLine();
                    lineaPartida = linea.split(" ");
                    coordenadas[j] = new Pair(Integer.parseInt(lineaPartida[0]), Integer.parseInt(lineaPartida[1]));
                }
                for (int k = 0; k <= numDesechos; k++) {
                    for (int j = 0; j <= numDesechos; j++) {
                        if (k == j) {
                            continue;
                        }
                        grafo.addArc(k, j,
                                Math.abs(coordenadas[k].first - coordenadas[j].first)
                                + Math.abs(coordenadas[k].second - coordenadas[j].second)
                        );
                    }
                }
                System.out.println("The shortest path has length " + recorrido(grafo));
            }
        } catch (Exception e) {
        }
    }
}
