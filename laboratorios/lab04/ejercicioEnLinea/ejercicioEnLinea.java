package lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ljpalaciom
 */
public class ejercicioEnLinea {

    public static void main(String[] args) {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer> qManana = new ArrayList<>();
        ArrayList<Integer> qTarde = new ArrayList<>();
        try {
            while (true) {
                String lineaPartida[] = lector.readLine().split(" ");
                int n = Integer.parseInt(lineaPartida[0]);
                int d = Integer.parseInt(lineaPartida[1]);
                int r = Integer.parseInt(lineaPartida[2]);
                if (n == 0) {
                    break;
                }
                lineaPartida = lector.readLine().split(" ");
                for (String hora : lineaPartida) {
                    qManana.add(Integer.parseInt(hora));
                }
                lineaPartida = lector.readLine().split(" ");
                for (String hora : lineaPartida) {
                    qTarde.add(Integer.parseInt(hora));
                }
                int dineroExtra = 0;
                int acum;
                Collections.sort(qManana);
                Collections.sort(qTarde);
                for (int i = 0; i < n; i++) {
                    acum = qManana.remove(qManana.size() - 1);
                    boolean entre = false;
                    int next = 0;
                    for (int j = qTarde.size() - 1; j >= 0; j--) {
                        next = qTarde.get(j);
                        if (acum + next <= d) {
                            qTarde.remove(j);
                            entre = true;
                            break;
                        }
                    }
                    if (!entre) {
                        qTarde.remove(0);
                        acum += next - d;
                        dineroExtra += acum * r;
                    }
                }
                System.out.println(dineroExtra);
            }
        } catch (Exception e) {
        }
    }
    
}
