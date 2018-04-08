/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab4;

import java.util.ArrayList;
import lab4.Digraph;
import lab4.Lab4;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alejandro Arroyave
 */
public class Lab4Test {
    
    public Lab4Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of costoTSP method, of class Lab4.
     */
    @Test
    public void testCostoTSP() {
        System.out.println("costoTSP");
        Digraph g = null;
        int expResult = 0;
        int result = Lab4.costoTSP(g);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of elVecinoMasCercanoParaTSP method, of class Lab4.
     */
    @Test
    public void testElVecinoMasCercanoParaTSP() {
        System.out.println("elVecinoMasCercanoParaTSP");
        System.out.println("d");
        Digraph g = new DigraphAL(4);
        g.addArc(0, 2, 15);
        g.addArc(2, 0, 15);
        g.addArc(0, 3, 20);
        g.addArc(3, 0, 20);
        g.addArc(0, 1, 10);
        g.addArc(1, 0, 10);
        g.addArc(3, 1, 25);
        g.addArc(1, 3, 25);
        g.addArc(3, 2, 30);
        g.addArc(2, 3, 30);
        g.addArc(1, 2, 35);
        g.addArc(2, 1, 35);
        g.addArc(0, 0, 0);
        g.addArc(1, 1, 0);
        g.addArc(2, 2, 0);
        g.addArc(3, 3, 0);
        ArrayList<Integer> expResult = new ArrayList<>();
        expResult.add(0);
        expResult.add(1);
        expResult.add(3);
        expResult.add(2);
        ArrayList<Integer> result = Lab4.elVecinoMasCercanoParaTSP(g);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
