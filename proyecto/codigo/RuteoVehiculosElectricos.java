package ruteovehiculoselectricos;

/**
 *
 * @author ljpalaciom
 */
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;

public class RuteoVehiculosElectricos {

    int n, m, u, breaks;
    double r, speed, Tmax, Smax, st_customer, Q;
    Digraph mapa;
    String filename;

    Pair<Float, Float>[] coordenadas;
    double tiempoSolucion;

    public RuteoVehiculosElectricos(String filename) {
        this.filename = filename;
        BufferedReader lector;
        String linea;
        String lineaPartida[];
        try {
            lector = new BufferedReader(new FileReader(filename));
            double[] valores = new double[10];
            for (int i = 0; i < 10; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                valores[i] = Float.parseFloat(lineaPartida[2]);
            }

            n = (int) valores[0];
            m = (int) valores[1];
            u = (int) valores[2];
            breaks = (int) valores[3];
            r = valores[4];
            speed = valores[5];
            Tmax = valores[6];
            Smax = valores[7];
            st_customer = valores[8];
            Q = valores[9];

            lector.readLine();
            lector.readLine();
            lector.readLine();

            coordenadas = new Pair[m + 1];
            mapa = new DigraphAL(m +1);
            while (m >= 0) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                if (lineaPartida[4].equals("d") || lineaPartida[4].equals("c")) {
                    coordenadas[Integer.parseInt(lineaPartida[0])] = new Pair(Float.parseFloat(lineaPartida[2]), Float.parseFloat(lineaPartida[3]));
                }
                m--;
            }

            for (int i = 0; i < mapa.size; i++) {
                for (int j = 0; j < mapa.size; j++) {
                    mapa.addArc(i, j, Math.sqrt(
                            Math.pow(coordenadas[i].first - coordenadas[j].first,
                                    2)
                            + Math.pow(coordenadas[i].second - coordenadas[j].second, 2)
                    )
                    );
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void solucionar() {
        //Dibujar ruta
        ArrayList<ArrayList<Integer>> rutas = elVecinoMasCercanoMultiRuta();
        System.out.println(rutas);
        System.out.println(this);
        //Dibujar
        int numRutas = rutas.size() / 5;
        for (int i = 0; i < 4; ++i) {
            JFrame frame = new JFrame("Algoritmo Voraz basado en el vecino mas cercano" + " rutas serie " + i);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            int limiteInferior = i * numRutas;
            int limiteSuperior = numRutas * (i + 1) - 1;
            frame.add(new DibujarRuta(coordenadas, rutas.subList(limiteInferior, limiteSuperior)));
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            frame.setBackground(new Color(255, 228, 196));
            frame.setVisible(true);
        }
        //soluFake();
    }

    private ArrayList<ArrayList<Integer>> elVecinoMasCercanoMultiRuta() {
        ArrayList<ArrayList<Integer>> rutasMultiples = new ArrayList<>();

        boolean isVisited[] = new boolean[mapa.size];
        boolean todosVisitados;
        double tiempoSolucionMaxima = -1.0;
        do {
            ArrayList<Integer> rutaActual = new ArrayList<>();
            rutasMultiples.add(rutaActual);
            int distActual[] = new int[1];
            todosVisitados = elVecinoMasCercanoParaTSP(rutaActual, distActual, isVisited);
            double tiempoSolucionActual = distActual[0] / speed;
            tiempoSolucionActual += (rutaActual.size()-2)* st_customer;
            if(tiempoSolucionMaxima < tiempoSolucionActual){
                tiempoSolucionMaxima = tiempoSolucionActual;
            }
        } while (!todosVisitados);
        tiempoSolucion = tiempoSolucionMaxima;
        return rutasMultiples;
    }

    private boolean elVecinoMasCercanoParaTSP(ArrayList<Integer> ruta, int distAcum[], boolean isVisited[]) {
        int posicionDondeEstaElDeposito = 0;
        int verticeActual = posicionDondeEstaElDeposito;
        int sucesorMenor = posicionDondeEstaElDeposito;
        ruta.add(verticeActual);
        boolean todosVisitados = false;
        double tiempoAcumulado = 0.0;
        while (tiempoAcumulado < 3) {
            ArrayList<Integer> sucesores = mapa.getSuccessors(verticeActual);
            isVisited[verticeActual] = true;
            double menorPeso = Double.MAX_VALUE;
            for (Integer sucesor : sucesores) {
                if (!isVisited[sucesor] && mapa.getWeight(verticeActual, sucesor) < menorPeso) {
                    menorPeso = mapa.getWeight(verticeActual, sucesor);
                    sucesorMenor = sucesor;
                }
            }
            if (sucesorMenor == verticeActual) {
                todosVisitados = true;
                break;
            }
           
            distAcum[0] += menorPeso;
            tiempoAcumulado = distAcum[0] / speed;
            tiempoAcumulado += (ruta.size()-2)* st_customer;
            
            ruta.add(sucesorMenor);
            verticeActual = sucesorMenor;
        }   
        
            distAcum[0] += mapa.getWeight(verticeActual, posicionDondeEstaElDeposito);
            ruta.add(posicionDondeEstaElDeposito);
        return todosVisitados;
    }

    public void soluFake(){
        double acum = 0;
        double distMayor = -1.0;
        for (int i = 0; i < coordenadas.length; i++) {
            if(distMayor < mapa.getWeight(0, i) * 2 ){
                distMayor = mapa.getWeight(0, i) * 2;
            }
        }
        System.out.println(distMayor);
        tiempoSolucion = distMayor / speed;
        tiempoSolucion += st_customer;
    }

    @Override
    public String toString() {
        return "RuteoVehiculosElectricos{" + "r=" + r + ", speed=" + speed + ", Tmax=" + Tmax + ", Smax=" + Smax + ", st_customer=" + st_customer + ", Q=" + Q + ", tiempoSolucion=" + tiempoSolucion + '}';
    }

    public static void main(String[] args) {
        File f = new File("E:\\Documentos\\NetBeansProjects\\RuteoVehiculosElectricos\\DataSets");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));
        RuteoVehiculosElectricos problema1 = new RuteoVehiculosElectricos("DataSets\\" + names.get(3));
        problema1.solucionar();
    }

}
