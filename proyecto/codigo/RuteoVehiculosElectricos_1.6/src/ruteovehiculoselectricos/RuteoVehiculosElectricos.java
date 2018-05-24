package ruteovehiculoselectricos;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.JFrame;

/**
 * Clase principal del proyecto
 *
 * @author ljpalaciom
 */
public class RuteoVehiculosElectricos {

    int n, m, u, breaks;
    double r, speed, Tmax, Smax, st_customer, Q;
    Digraph mapa;
    short tipoEstacion[];
    float pendienteFuncionCarga[];
    String filename;
    Pair<Float, Float>[] coordenadas;
    int cantidadRutas;
    double tiempoSolucion;

    /**
     * El constructor de la clase. Inicializa variables con base en el archivo
     * que se le entrega
     *
     * @param filename Un archivo que contiene todos los datos del problema
     */
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
        return "RuteoVehiculosElectricos{" + "r=" + r + ", speed=" + speed + ", Tmax=" + Tmax + ", Smax=" + Smax + ", st_customer=" + st_customer + ", Q=" + Q + ", tiempoSolucion=" + tiempoSolucion + "h" + '}';
    }

    /**
     * Este metodo imprime la solucion del problema
     */
    public void solucionar() {
        ArrayList<ArrayList<Pair<Integer, Double>>> rutas = elVecinoMasCercanoMultiRuta();
        long tiempo = 25;
        for (int i = 0; i < 1000000; i++) {

            int indiceRuta1 = (int) Math.floor(Math.random() * rutas.size());
            int indiceRuta2 = (int) Math.floor(Math.random() * rutas.size());
            intercambiarDosPuntos(rutas.get(indiceRuta1), rutas.get(indiceRuta2));
        }
        actualizarTiempoDeSolucion(rutas);
        System.out.println(this);
        boolean esCorrecto = comprobarSolucion(rutas);
        System.out.println(esCorrecto);
        cantidadRutas = rutas.size();
        // System.out.println(rutas);
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
     *
     * @param rutas Es un contenedor de rutas representadas por un arraylist de
     * parejas donde el primer elemento indica el nodo y el segundo elemento el
     * tiempo que se quedo en ese nodo
     * @return Verdadero si el tiempo de solucion expresado concuerda, si la
     * bateria nunca esta por debajo de 0 y si se corrobora que se visitaron
     * todos los clientes.
     */
    public boolean comprobarSolucion(ArrayList<ArrayList<Pair<Integer, Double>>> rutas) {
        double disTotal = 0.0, tiempoVisitaTotal = 0.0, tiempo;
        int numRuta = 1;
        HashSet<Integer> clientes = new HashSet<>();
        for (int i = 0; i <= m; i++) {
            clientes.add(i);
        }
        for (ArrayList<Pair<Integer, Double>> ruta : rutas) {
            double dis = 0.0;
            double tiempoVisita = 0.0;
            double bateriaActual = Q;
            for (int i = 0; i < ruta.size() - 1; i++) {
                tiempoVisita += ruta.get(i).second;
                tiempoVisitaTotal += ruta.get(i).second;
                disTotal += mapa.getWeight(ruta.get(i).first, ruta.get(i + 1).first);
                dis += mapa.getWeight(ruta.get(i).first, ruta.get(i + 1).first);
                bateriaActual -= mapa.getWeight(ruta.get(i).first, ruta.get(i + 1).first) * r;
                if (ruta.get(i).first >= m + 1) {
                    double pendienteCarga = pendienteFuncionCarga[tipoEstacion[ruta.get(i).first - m - 1]];
                    bateriaActual += pendienteCarga * ruta.get(i).second;
                }
                if (bateriaActual < 0) {
                    System.out.println("que pato");
                    return false;
                }
                clientes.remove(ruta.get(i).first);
            }
            //System.out.println("Ruta#" + numRuta + " " + (dis / speed + tiempoVisita)*60 + " minutos");
            if (dis / speed + tiempoVisita > 10) {
                return false;
            }
            numRuta++;
        }
        tiempo = disTotal / speed + tiempoVisitaTotal;

        if (Math.abs(tiempoSolucion - tiempo) > 0.0001 || !clientes.isEmpty()) {
            System.out.println("Numero de clientes " + clientes.size());
            return false;
        }

        return true;
    }

    public void actualizarTiempoDeSolucion(ArrayList<ArrayList<Pair<Integer, Double>>> rutas) {
        double disTotal = 0.0, tiempoVisitaTotal = 0.0;
        for (ArrayList<Pair<Integer, Double>> ruta : rutas) {
            for (int i = 0; i < ruta.size() - 1; i++) {
                tiempoVisitaTotal += ruta.get(i).second;
                disTotal += mapa.getWeight(ruta.get(i).first, ruta.get(i + 1).first);
            }
        }
        tiempoSolucion = disTotal / speed + tiempoVisitaTotal;
    }

    public void intercambiarDosPuntos(ArrayList<Pair<Integer, Double>> ruta1, ArrayList<Pair<Integer, Double>> ruta2) {

        double tiempoDeRuta1;
        double tiempoDeRuta2;
        double tiempoActual = 0.0;
        try {
            tiempoDeRuta1 = tiempoDeRuta(ruta1);
            tiempoDeRuta2 = tiempoDeRuta(ruta2);
            tiempoActual = tiempoDeRuta1 + tiempoDeRuta2;
        } catch (Exception e) {
            System.out.println("Imposibile");
        }
        for (int i = 0; i < 10; i++) {
            int indicePunto1 = (int) Math.floor(Math.random() * (ruta1.size() - 2) + 1);
            int indicePunto2 = (int) Math.floor(Math.random() * (ruta2.size() - 2) + 1);
            Pair<Integer, Double> temp = ruta1.get(indicePunto1);
            ruta1.set(indicePunto1, ruta2.get(indicePunto2));
            ruta2.set(indicePunto2, temp);

//            Pair<Integer, Double> porSiAcaso1 = null;
//            Pair<Integer, Double> porSiAcaso2 = null;
//            if (ruta1.get(indicePunto1).first >= m + 1) {
//                porSiAcaso1 = ruta1.remove(indicePunto1);
//            }
//            if (ruta2.get(indicePunto2).first >= m + 1) {
//                porSiAcaso2 = ruta2.remove(indicePunto2);
//            }
            try {
                tiempoDeRuta1 = tiempoDeRuta(ruta1);
                tiempoDeRuta2 = tiempoDeRuta(ruta2);
                double tiempoNuevo = tiempoDeRuta1 + tiempoDeRuta2;
                if (tiempoActual < tiempoNuevo) {
                    throw new Exception("Antes estabamos mejor");
                }
                tiempoActual = tiempoNuevo;
            } catch (Exception e) {
//                if (porSiAcaso1 != null) {
//                    ruta1.add(indicePunto1, porSiAcaso1);
//
//                }
//                if (porSiAcaso2 != null) {
//                    ruta2.add(indicePunto2, porSiAcaso2);
//                }
                
                temp = ruta1.get(indicePunto1);
                ruta1.set(indicePunto1, ruta2.get(indicePunto2));
                ruta2.set(indicePunto2, temp);
            }
        }
    }

    public double tiempoDeRuta(ArrayList<Pair<Integer, Double>> ruta) throws Exception {

        double dis = 0.0;
        double tiempoVisita = 0.0;
        double bateriaActual = Q;
        for (int i = 0; i < ruta.size() - 1; i++) {
            tiempoVisita += ruta.get(i).second;
            dis += mapa.getWeight(ruta.get(i).first, ruta.get(i + 1).first);
            bateriaActual -= mapa.getWeight(ruta.get(i).first, ruta.get(i + 1).first) * r;
            if (ruta.get(i).first >= m + 1) {
                double pendienteCarga = pendienteFuncionCarga[tipoEstacion[ruta.get(i).first - m - 1]];
                bateriaActual += pendienteCarga * ruta.get(i).second;
            }
            if (bateriaActual < 0) {
                throw new Exception("No por bateria");
            }
        }
        if (dis / speed + tiempoVisita > 10) {
            throw new Exception("No por tiempo");
        }
        return dis / speed + tiempoVisita;
    }

    /**
     * Este metedo se encarga de generar multiples rutas que son solucion al
     * problema
     *
     * @return Devuelve un contenedor de rutas representadas por un arraylist de
     * parejas donde el primer elemento indica el nodo y el segundo elemento el
     * tiempo que se quedo en ese nodo
     */
    private ArrayList<ArrayList<Pair<Integer, Double>>> elVecinoMasCercanoMultiRuta() {
        ArrayList<ArrayList<Pair<Integer, Double>>> solucionMenor = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            int paramBateria = 500 * i + 1000;
            for (int j = 0; j < 30; j++) {
                ArrayList<ArrayList<Pair<Integer, Double>>> rutasMultiplesTemp = new ArrayList<>();
                boolean isVisited[] = new boolean[mapa.size];
                double tiempoAcum = 0.0;
                boolean todosVisitados;
                int numRuta = 0;
                try {
                    do {
                        ArrayList<Pair<Integer, Double>> rutaActual = new ArrayList<>();
                        rutasMultiplesTemp.add(rutaActual);
                        double distActual[] = new double[1];
                        int numClientes[] = {0};
                        double tiempoCarga[] = {0};
                        float tiempoParam = (float) (j / 10. + 7.8);
                        todosVisitados = elVecinoMasCercanoParaTSP(rutaActual, distActual, isVisited, numClientes, tiempoCarga, tiempoParam, paramBateria);
                        double tiempoSolucionActual = distActual[0] / speed;
                        tiempoSolucionActual += numClientes[0] * st_customer;
                        tiempoSolucionActual += tiempoCarga[0];
                        tiempoAcum += tiempoSolucionActual;
                        numRuta++;
                        if (numRuta > 100) {
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
        }

        return solucionMenor;
    }

    /**
     * Este metodo está basado en el algoritmo voraz del vecino más cercano y ha
     * sido modificado para aceptar las restricciones de este problema
     *
     * @param ruta Es un arraylist que se irá modificando para generar una ruta
     * del problema.
     * @param distAcum Es un arreglo de una sola posicion que representa la
     * distancia que se ha acumulado en el trayecto
     * @param isVisited Un arreglo de booleanos que representa los clientes
     * visitados hasta el momento
     * @param numClientes Es un arreglo de una sola posicion que representa la
     * cantidad de clientes visitados por la presente ruta
     * @param tiempoCarga Es un arreglo de una sola posicion que representa el
     * tiempo total invertido en recargar el automovil
     * @param paramTiempo Es un parametro que cambia considerablemente el
     * resultado de este algoritmo, para generar una mejor solucion se cambia
     * constantemente para obtener un tiempo más óptimimo
     * @param paramBateria Es un parametro que cambia considerablemente el
     * resultado de este algoritmo, para generar una mejor solucion se cambia
     * constantemente para obtener un tiempo más óptimimo
     *
     * @return Verdadero si ya se visitaron todos los clientes y falso de lo
     * contrario
     * @throws Exception Lanza excepcion si en algun momento una ruta supera el
     * tiempo de restriccion Tmax, o si en algun momento una ruta genera valores
     * negativos de bateria para un auto electrico que la recorra
     */
    private boolean elVecinoMasCercanoParaTSP(ArrayList<Pair<Integer, Double>> ruta, double distAcum[], boolean isVisited[], int numClientes[], double[] tiempoCarga, float paramTiempo, int paramBateria) throws Exception {
        int posicionDondeEstaElDeposito = 0;
        int verticeActual = posicionDondeEstaElDeposito;
        int sucesorMenor = posicionDondeEstaElDeposito;
        ruta.add(new Pair(verticeActual, 0.0));
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
            if (tiempoHipotetico > paramTiempo) {
                numClientes[0]--;
                bateriaActual += menorPeso * r;
                tiempoActual -= menorPeso / speed;
                tiempoActual -= st_customer;
                distAcum[0] -= menorPeso;
                break;
            }

            if (bateriaActual < paramBateria) {
                numClientes[0]--;
                bateriaActual += menorPeso * r;
                tiempoActual -= menorPeso / speed;
                tiempoActual -= st_customer;
                distAcum[0] -= menorPeso;
                if (tiempoActual < 6) {
                    int posibleNuevoObjetivo = sucesorMenor;
                    sucesorMenor = verticeActual;
                    boolean alDeposito = false;

                    menorPeso = Double.MAX_VALUE;
                    double menorTiempo = Double.MAX_VALUE;
                    while (true) {
                        for (int i = 0; i < u; ++i) {
                            double bateriaHipotetica = bateriaActual - (mapa.getWeight(verticeActual, m + 1 + i) * r);
                            if (bateriaHipotetica < 10) {
                                continue;
                            }
                            double tiempoYendo = mapa.getWeight(verticeActual, m + 1 + i) / speed;
                            double pendienteCarga = pendienteFuncionCarga[tipoEstacion[i]];
                            double tiempoCargaCompleta = (Q - bateriaActual) / pendienteCarga;
                            double tiempoANodoHipotetico = mapa.getWeight(m + 1 + i, posibleNuevoObjetivo);
                            double tiempoTotal = tiempoCargaCompleta + tiempoYendo + tiempoANodoHipotetico;
                            if (tiempoTotal < menorTiempo) {
                                menorTiempo = tiempoTotal;
                                menorPeso = mapa.getWeight(verticeActual, m + 1 + i);
                                sucesorMenor = m + 1 + i;
                            }
                        }

                        if (menorTiempo == Double.MAX_VALUE) {
                            if (posibleNuevoObjetivo == verticeActual) {
                                alDeposito = true;
                                break;
                            }
                            posibleNuevoObjetivo = verticeActual;
                        } else {
                            break;
                        }
                    }

                    if (alDeposito) {
                        break;
                    }
                    tiempoActual += menorPeso / speed;
                    distAcum[0] += menorPeso;
                    bateriaActual -= menorPeso * r;
                    //recargar la bateria

                    if (bateriaActual < 12000) {
                        double pendienteCarga = pendienteFuncionCarga[tipoEstacion[sucesorMenor - m - 1]];
                        double tiempoCargaCompleta = (Q - bateriaActual) / pendienteCarga;
                        bateriaActual += pendienteCarga * tiempoCargaCompleta;
                        tiempoActual += tiempoCargaCompleta;
                        tiempoCarga[0] += tiempoCargaCompleta;
                        ruta.add(new Pair(sucesorMenor, tiempoCargaCompleta));
                    } else {
                        ruta.add(new Pair(sucesorMenor, 0.0));
                    }
                } else {
                    break;
                }
            }
            if (sucesorMenor <= m) {
                ruta.add(new Pair(sucesorMenor, st_customer));
            }
            verticeActual = sucesorMenor;
        }
        bateriaActual -= mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) * r;
        if (bateriaActual
                < 0) {
            bateriaActual += mapa.getWeight(verticeActual, posicionDondeEstaElDeposito) * r;
            menorPeso = Double.MAX_VALUE;
            double menorTiempo = Double.MAX_VALUE;
            for (int i = 0; i < u; ++i) {
                double bateriaHipotetica = bateriaActual - (mapa.getWeight(verticeActual, m + 1 + i) * r);
                if (bateriaHipotetica < 10) {
                    continue;
                }
                double tiempoYendo = mapa.getWeight(verticeActual, m + 1 + i) / speed;
                double bateriaNecesaria = mapa.getWeight(m + 1 + i, posicionDondeEstaElDeposito) * r + 10;
                if (bateriaNecesaria > Q) {
                    continue;
                }
                double pendienteCarga = pendienteFuncionCarga[tipoEstacion[i]];
                double tiempoCargaNecesaria = (bateriaNecesaria - bateriaActual) / pendienteCarga;
                double tiempoADeposito = mapa.getWeight(m + 1 + i, posicionDondeEstaElDeposito);
                double tiempoTotal = tiempoCargaNecesaria + tiempoYendo + tiempoADeposito;

                if (tiempoTotal < menorTiempo) {
                    menorTiempo = tiempoTotal;
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
                ruta.add(new Pair(sucesorMenor, tiempoCargaCompleta));
            } else {
                ruta.add(new Pair(sucesorMenor, 0.0));
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

        ruta.add(
                new Pair(posicionDondeEstaElDeposito, 0.0));
        return todosVisitados;

    }

    /**
     * Clase principal de la clase.
     *
     * @param args
     */
    public static void main(String[] args) {
        File f = new File("E:\\Documentos\\NetBeansProjects\\RuteoVehiculosElectricos\\DataSets");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));
        System.out.println("Archivo           N Rutas   Tiempo");
        double acum = 0.;
        int numRutas = 0;
        for (int i = 0; i < 12; i++) {
            RuteoVehiculosElectricos problema1 = new RuteoVehiculosElectricos("DataSets\\" + names.get(i));
            problema1.solucionar();
            acum += problema1.tiempoSolucion;
            numRutas += problema1.cantidadRutas;
        }
        System.out.println("Total " + acum + "num rutas " + numRutas);

    }

}
