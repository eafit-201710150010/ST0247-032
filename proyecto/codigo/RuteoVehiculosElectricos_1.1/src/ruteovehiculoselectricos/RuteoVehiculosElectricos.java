package ruteovehiculoselectricos;

/**
 *
 * @author ljpalaciom
 */
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;

public class RuteoVehiculosElectricos {

    int n, m, u, breaks;
    double r, speed, Tmax, Smax, st_customer, Q;
    Digraph mapa;
    short tipoEstacion[];
    float pendienteFuncionCarga[];
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

            coordenadas = new Pair[n];
            mapa = new DigraphAM(n);
            for (int i = 0; i <= m; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                coordenadas[Integer.parseInt(lineaPartida[0])] = new Pair(Float.parseFloat(lineaPartida[2]), Float.parseFloat(lineaPartida[3]));
            }
            tipoEstacion = new short[u];
            for (int i = 0; i < u; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                coordenadas[Integer.parseInt(lineaPartida[0])] = new Pair(Float.parseFloat(lineaPartida[2]), Float.parseFloat(lineaPartida[3]));
                tipoEstacion[i] = Short.parseShort(lineaPartida[5]);
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    mapa.addArc(i, j, Math.sqrt(
                            Math.pow(coordenadas[i].first - coordenadas[j].first,
                                    2)
                            + Math.pow(coordenadas[i].second - coordenadas[j].second, 2)
                    )
                    );
                }
            }

            pendienteFuncionCarga = new float[3];
            lector.readLine();
            lector.readLine();
            lector.readLine();
            for (int i = 0; i < 3; ++i) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                pendienteFuncionCarga[i] = Float.parseFloat(lineaPartida[3]);
            }
            lector.readLine();
            lector.readLine();
            lector.readLine();
            for (int i = 0; i < 3; ++i) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                pendienteFuncionCarga[i] = Float.parseFloat(lineaPartida[3]) / pendienteFuncionCarga[i];
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void solucionar() {
        try {
            ArrayList<ArrayList<Integer>> rutas = elVecinoMasCercanoMultiRuta();
            System.out.println(rutas);
            System.out.println(this);
        } catch (Exception e) {
            System.out.println("No se encontró solución");
        }
        //Dibujar
//        int numRutas = rutas.size() / 5;
//        for (int i = 0; i < 4; ++i) {
//            JFrame frame = new JFrame("Algoritmo Voraz basado en el vecino mas cercano" + " rutas serie " + i);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            int limiteInferior = i * numRutas;
//            int limiteSuperior = numRutas * (i + 1) - 1;
//            frame.add(new DibujarRuta(coordenadas, rutas.subList(limiteInferior, limiteSuperior)));
//            frame.setSize(1200, 800);
//            frame.setLocationRelativeTo(null);
//            frame.setBackground(new Color(255, 228, 196));
//            frame.setVisible(true);
//        }
    }

    private ArrayList<ArrayList<Integer>> elVecinoMasCercanoMultiRuta() throws Exception {
        ArrayList<ArrayList<Integer>> rutasMultiples = new ArrayList<>();

        boolean isVisited[] = new boolean[mapa.size];
        boolean todosVisitados;
        double tiempoAcum = 0.0;
        int numRuta = 0;
        do {
            ArrayList<Integer> rutaActual = new ArrayList<>();
            rutasMultiples.add(rutaActual);
            double distActual[] = new double[1];
            int numClientes[] = {0};
            double tiempoCarga[] = {0};
            todosVisitados = elVecinoMasCercanoParaTSP(rutaActual, distActual, isVisited, numClientes, tiempoCarga);
            double tiempoSolucionActual = distActual[0] / speed;
            tiempoSolucionActual += numClientes[0] * st_customer;
            tiempoSolucionActual += tiempoCarga[0];
            System.out.println("Ruta #" + numRuta + " Tarda: " + tiempoSolucionActual + " min");
            tiempoAcum += tiempoSolucionActual;

            numRuta++;
        } while (!todosVisitados);
        tiempoSolucion = tiempoAcum;
        return rutasMultiples;
    }

    private boolean elVecinoMasCercanoParaTSP(ArrayList<Integer> ruta, double distAcum[], boolean isVisited[], int numClientes[], double[] tiempoCarga) throws Exception {
        int posicionDondeEstaElDeposito = 0;
        int verticeActual = posicionDondeEstaElDeposito;
        int sucesorMenor = posicionDondeEstaElDeposito;
        ruta.add(verticeActual);
        boolean todosVisitados = false;
        double tiempoActual = 0.0;
        double bateriaActual = Q;
        double menorPeso;
        while (true) {
            ArrayList<Integer> sucesores = mapa.getSuccessors(verticeActual);
            isVisited[verticeActual] = true;
            menorPeso = Double.MAX_VALUE;
            for (Integer sucesor : sucesores) {
                if (sucesor > m) {
                    continue;
                }
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
            tiempoActual += menorPeso / speed;
            tiempoActual += st_customer;
            bateriaActual -= menorPeso * r;
            numClientes[0]++;
            double tiempoHipotetico = tiempoActual + mapa.getWeight(sucesorMenor, posicionDondeEstaElDeposito) / speed;

            if (bateriaActual < 6700) {
                numClientes[0]--;
                bateriaActual += menorPeso * r;
                tiempoActual -= menorPeso / speed;
                tiempoActual -= st_customer;
                distAcum[0] -= menorPeso;
                if (tiempoActual < 6) {
                    int posibleNuevoObjetivo = sucesorMenor;
                    menorPeso = Double.MAX_VALUE;
                    boolean[] descartado = new boolean[tipoEstacion.length];
                    while (true) {
                        for (int i = 0; i < u; ++i) {
                            if (verticeActual >= m + 1 + i) {
                                continue;
                            }
                            if (!descartado[m + 1 + i] && mapa.getWeight(posibleNuevoObjetivo, m + 1 + i) < menorPeso) {
                                menorPeso = mapa.getWeight(verticeActual, m + 1 + i);
                                sucesorMenor = m + 1 + i;
                            }
                        }
                        descartado[sucesorMenor] = true;
                        bateriaActual -= menorPeso * r;
                        if(verticeActual == sucesorMenor){
                            throw new Exception();
                        }
                        if (bateriaActual < 0) {
                            bateriaActual += menorPeso * r;
                        } else {
                            break;
                        }
                    }
                    tiempoActual += menorPeso / speed;
                    distAcum[0] += menorPeso;
                    //recargar la bateria
                    if (bateriaActual < 11000) {
                        double pendienteCarga = pendienteFuncionCarga[tipoEstacion[sucesorMenor - m - 1]];
                        double tiempoCargaCompleta = (Q - bateriaActual) / pendienteCarga;
                        bateriaActual += pendienteCarga * tiempoCargaCompleta;
                        tiempoActual += tiempoCargaCompleta;
                        tiempoCarga[0] += tiempoCargaCompleta;
                    }
                } else {
                    break;
                }
            } else if (tiempoHipotetico > 8.5) {
                numClientes[0]--;
                bateriaActual += menorPeso * r;
                tiempoActual -= menorPeso / speed;
                tiempoActual -= st_customer;
                distAcum[0] -= menorPeso;
                break;
            }

            ruta.add(sucesorMenor);
            verticeActual = sucesorMenor;
        }

        bateriaActual -= mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) * r;

        if (bateriaActual < 0) {
            bateriaActual += mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) * r;
            menorPeso = Double.MAX_VALUE;
            for (int i = 0; i < u; ++i) {
                if (verticeActual == m + 1 + i) {
                    continue;
                }
                if (mapa.getWeight(verticeActual, m + 1 + i) < menorPeso) {
                    menorPeso = mapa.getWeight(verticeActual, m + 1 + i);
                    sucesorMenor = m + 1 + i;
                }
            }
            distAcum[0] += menorPeso;
            bateriaActual -= menorPeso * r;
            tiempoActual += menorPeso / speed;
            if (bateriaActual < 0) {
                throw new Exception();
            }
            double pendienteCarga = pendienteFuncionCarga[tipoEstacion[sucesorMenor - m - 1]];
            double tiempoCargaCompleta = (Q - bateriaActual) / pendienteCarga;
            bateriaActual += pendienteCarga * tiempoCargaCompleta;
            tiempoActual += tiempoCargaCompleta;
            tiempoCarga[0] += tiempoCargaCompleta;
            verticeActual = sucesorMenor;
        }

        distAcum[0] += mapa.getWeight(verticeActual, posicionDondeEstaElDeposito);
        ruta.add(posicionDondeEstaElDeposito);
        return todosVisitados;
    }

    @Override
    public String toString() {
        return "RuteoVehiculosElectricos{" + "r=" + r + ", speed=" + speed + ", Tmax=" + Tmax + ", Smax=" + Smax + ", st_customer=" + st_customer + ", Q=" + Q + ", tiempoSolucion=" + tiempoSolucion + '}';
    }

    public void exportarPuntosCSV() {
        try {
            PrintStream escribirCoordenadas = new PrintStream(new File("ArchivosGenerados\\Coordenadas.csv"));
            escribirCoordenadas.println("X,Y");
            for (Pair<Float, Float> coordenada : coordenadas) {
                escribirCoordenadas.println(coordenada.first + "," + coordenada.second);
            }
            escribirCoordenadas.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void exportarRutasCSV(ArrayList<ArrayList<Integer>> rutas) {
        try {
            int numRuta = 0;
            for (ArrayList<Integer> ruta : rutas) {
                PrintStream escribirCoordenadas = new PrintStream(new File("ArchivosGenerados\\ruta" + numRuta + ".csv"));
                escribirCoordenadas.println("X,Y");
                for (Integer verticeActual : ruta) {
                    escribirCoordenadas.println(coordenadas[verticeActual].first + "," + coordenadas[verticeActual].second);
                }
                escribirCoordenadas.close();
                numRuta++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        File f = new File("E:\\Documentos\\NetBeansProjects\\RuteoVehiculosElectricos\\DataSets");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));

        RuteoVehiculosElectricos problema1 = new RuteoVehiculosElectricos("DataSets\\" + names.get(9));
        problema1.solucionar();

    }

}
