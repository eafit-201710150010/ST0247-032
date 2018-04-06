/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller7;

import java.util.Comparator;


/**
 *
 * @author ljpalaciom
 */
public class Comparador implements Comparator <Pair> {

    @Override
    public int compare(Pair p1, Pair p2) {
        return (int) p1.second < (int) p2.second ? -1 : (p1.second == p2.second ? 0 : 1);
    }

   
}
