package ruteovehiculoselectricos;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 * Una clase que permite hacer una visualizacion de las soluciones encontradas al problema
 * @author ljpalaciom
 */
public class DibujarRuta extends JPanel {

    Pair[] coordenadas;
    List<ArrayList<Pair<Integer,Double>>> rutas;

    /**
     * Este es el constructor de la clase
     * @param coordenadas Las coordenadas de todos los puntos a dibujar
     * @param rutas Las rutas que se desean dibujar
     */
    public DibujarRuta(Pair[] coordenadas, List<ArrayList<Pair<Integer,Double>>> rutas) {
        this.coordenadas = coordenadas;
        this.rutas = rutas;
    }

    @Override
    public void paintComponent(Graphics g) {
        DibujarLineas(g);
    }

    /**
     * Este método se encarga de dibujar las rutas
     * @param g El componente de graphics que permite hacer los dibujos
     */
    private void DibujarLineas(Graphics g) {

        for (List<Pair<Integer,Double>> ruta : rutas) {
            int xInicial, yInicial, xFinal, yFinal;
            xInicial = Math.round((float) coordenadas[ruta.get(0).first].first);
            yInicial = Math.round((float) coordenadas[ruta.get(0).first].second);

            for (int i = 0; i < ruta.size(); ++i) {
                int actual = ruta.get(i).first;
                xFinal = Math.round((float) coordenadas[actual].first);
                yFinal = Math.round((float) coordenadas[actual].second);

                g.setColor(Color.DARK_GRAY);
                g.drawLine(xInicial * 5 + 250, yInicial * 5 + 40, xFinal * 5 + 250, yFinal * 5 + 40);
                // g.drawString(String.valueOf(actual), xFinal*5 + 250,yFinal*5 + 40);
                if (actual != 0) {
                    g.setColor(Color.red);
                    g.fillOval(xFinal * 5 + 250, yFinal * 5 + 40, 5, 5);
                }
                xInicial = xFinal;
                yInicial = yFinal;
            }

            g.setColor(Color.GREEN);
            g.fillOval(xInicial * 5 + 240, yInicial * 5 + 30, 20, 20);
        }
    }
}
