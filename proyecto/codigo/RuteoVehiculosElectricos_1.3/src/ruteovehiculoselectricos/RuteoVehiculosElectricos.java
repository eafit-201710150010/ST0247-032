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
            tiempoSolucion = Double.MAX_VALUE;
        } catch (Exception ex) {
            System.out.println(ex);
        }
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

    public void solucionar() {
        ArrayList<ArrayList<Pair<Integer, Integer>>> rutas = elVecinoMasCercanoMultiRuta();
        System.out.println(rutas);
        System.out.println(this);
        
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
    /**
     * Este metodo es un test para verificar que la solucion es correcta. 
     * @param rutas Es un contenedor de rutas representadas por un arraylist de parejas donde el primer elemento indica el nodo
     * y el segundo elemento el tiempo que se quedo en ese nodo
     * @return Verdadero si el tiempo de solucion expresado concuerda y si la bateria nunca esta por debajo de 0.
     */
    public boolean comprobarSolucion( ArrayList<ArrayList<Pair<Integer, Integer>>> rutas){
        return false;
    }
    
    private ArrayList<ArrayList<Pair<Integer, Integer>>> elVecinoMasCercanoMultiRuta() {
        ArrayList<ArrayList<Pair<Integer, Integer>>> solucionMenor = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ArrayList<ArrayList<Pair<Integer, Integer>>> rutasMultiplesTemp = new ArrayList<>();
            boolean isVisited[] = new boolean[mapa.size];
            double tiempoAcum = 0.0;
            boolean todosVisitados;
            int numRuta = 0;
            try {
                do {
                    ArrayList<Pair<Integer, Integer>> rutaActual = new ArrayList<>();
                    rutasMultiplesTemp.add(rutaActual);
                    double distActual[] = new double[1];
                    int numClientes[] = {0};
                    double tiempoCarga[] = {0};
                    float tiempoParam = (float) (i / 10. + 7.8);
                    todosVisitados = elVecinoMasCercanoParaTSP(rutaActual, distActual, isVisited, numClientes, tiempoCarga, tiempoParam);
                    double tiempoSolucionActual = distActual[0] / speed;
                    tiempoSolucionActual += numClientes[0] * st_customer;
                    tiempoSolucionActual += tiempoCarga[0];
                    tiempoAcum += tiempoSolucionActual;
                    numRuta++;
                    if (numRuta > 200) {
                        break;
                    }
                } while (!todosVisitados);
                if (!todosVisitados) {
                    throw new Exception("No se pudieron visitar todos");
                }
                if (tiempoAcum < tiempoSolucion) {
                    tiempoSolucion = tiempoAcum;
                    solucionMenor = rutasMultiplesTemp;
                }
            } catch (Exception e) {
            }
        }

        return solucionMenor;
    }

    private boolean elVecinoMasCercanoParaTSP(ArrayList<Pair<Integer, Integer>> ruta, double distAcum[], boolean isVisited[], int numClientes[], double[] tiempoCarga, float paramTiempo) throws Exception {
        int posicionDondeEstaElDeposito = 0;
        int verticeActual = posicionDondeEstaElDeposito;
        int sucesorMenor = posicionDondeEstaElDeposito;
        ruta.add(new Pair(verticeActual, 0));
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
            double menorPesoEstacion = Integer.MAX_VALUE;
            for (int i = 0; i < u; ++i) {
                if (mapa.getWeight(sucesorMenor, m + 1 + i) < menorPesoEstacion) {
                    menorPesoEstacion = mapa.getWeight(verticeActual, m + 1 + i);
                }
            }
            double tiempoHipotetico = tiempoActual + mapa.getWeight(sucesorMenor, posicionDondeEstaElDeposito) / speed;
            double bateriaHipotetica = bateriaActual - menorPesoEstacion * r;

            if (bateriaActual < 4000) {
                numClientes[0]--;
                bateriaActual += menorPeso * r;
                tiempoActual -= menorPeso / speed;
                tiempoActual -= st_customer;
                distAcum[0] -= menorPeso;
                if (tiempoActual < 6) {
                    int posibleNuevoObjetivo = sucesorMenor;
                    sucesorMenor = verticeActual;
                    boolean alDeposito = false;
                    boolean[] descartado = new boolean[tipoEstacion.length];
                    while (true) {
                        menorPeso = Double.MAX_VALUE;
                        for (int i = 0; i < u; ++i) {
                            if (!descartado[i] && mapa.getWeight(posibleNuevoObjetivo, m + 1 + i) < menorPeso) {
                                menorPeso = mapa.getWeight(verticeActual, m + 1 + i);
                                sucesorMenor = m + 1 + i;
                            }
                        }
                        if (menorPeso == Double.MAX_VALUE || sucesorMenor == verticeActual) {
                            if (posibleNuevoObjetivo == verticeActual) {
                                alDeposito = true;
                                break;
                            }
                            descartado = new boolean[tipoEstacion.length];
                            posibleNuevoObjetivo = verticeActual;
                            continue;
                        }
                        descartado[sucesorMenor - m - 1] = true;
                        bateriaActual -= menorPeso * r;
                        if (bateriaActual < 0) {
                            bateriaActual += menorPeso * r;
                        } else {
                            break;
                        }

                    }
                    if (alDeposito) {
                        break;
                    }
                    tiempoActual += menorPeso / speed;
                    distAcum[0] += menorPeso;
                    //recargar la bateria
                    if (bateriaActual < 12000) {
                        double pendienteCarga = pendienteFuncionCarga[tipoEstacion[sucesorMenor - m - 1]];
                        double tiempoCargaCompleta = (Q - bateriaActual) / pendienteCarga;
                        bateriaActual += pendienteCarga * tiempoCargaCompleta;
                        tiempoActual += tiempoCargaCompleta;
                        tiempoCarga[0] += tiempoCargaCompleta;
                        ruta.add(new Pair(sucesorMenor, tiempoCargaCompleta));
                    } else {
                        ruta.add(new Pair(sucesorMenor, 0));
                    }
                } else {
                    break;
                }
            } else if (tiempoHipotetico > paramTiempo) {
                numClientes[0]--;
                bateriaActual += menorPeso * r;
                tiempoActual -= menorPeso / speed;
                tiempoActual -= st_customer;
                distAcum[0] -= menorPeso;
                break;
            }
            if (sucesorMenor <= m) {
                ruta.add(new Pair(sucesorMenor, st_customer));
            }
            verticeActual = sucesorMenor;
        }
        bateriaActual -= mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) * r;
        if (bateriaActual < 0) {
            bateriaActual += mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) * r;
            menorPeso = Double.MAX_VALUE;
            for (int i = 0; i < u; ++i) {
                if (mapa.getWeight(verticeActual, m + 1 + i) < menorPeso) {
                    menorPeso = mapa.getWeight(verticeActual, m + 1 + i);
                    sucesorMenor = m + 1 + i;
                }
            }
            distAcum[0] += menorPeso;
            bateriaActual -= menorPeso * r;
            tiempoActual += menorPeso / speed;
            if (bateriaActual < 0) {
                throw new Exception("No hay solucion por bateria, error 1");
            }
            verticeActual = sucesorMenor;
            double bateriaNecesaria = mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) * r + 10;
            if (bateriaActual < bateriaNecesaria) {
                double pendienteCarga = pendienteFuncionCarga[tipoEstacion[sucesorMenor - m - 1]];
                double tiempoCargaCompleta = (bateriaNecesaria - bateriaActual) / pendienteCarga;
                bateriaActual += pendienteCarga * tiempoCargaCompleta;
                tiempoActual += tiempoCargaCompleta;
                tiempoCarga[0] += tiempoCargaCompleta;
            }
            bateriaActual -= mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) * r;
            if (bateriaActual < 0) {
                throw new Exception("No hay solucion por bateria, error 2");
            }
        }

        distAcum[0] += mapa.getWeight(verticeActual, posicionDondeEstaElDeposito);
        tiempoActual += mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) / speed;
        if (tiempoActual > 10) {
            throw new Exception("No hay solucion por tiempo");
        }
        ruta.add(new Pair(posicionDondeEstaElDeposito, 0));

        return todosVisitados;

    }

    public static void main(String[] args) {
        File f = new File("E:\\Documentos\\NetBeansProjects\\RuteoVehiculosElectricos\\DataSets");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));

        RuteoVehiculosElectricos problema1 = new RuteoVehiculosElectricos("DataSets\\" + names.get(6));
        problema1.solucionar();

    }

}
