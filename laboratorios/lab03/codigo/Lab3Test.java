/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import lab3.*;
import static lab3.Lab3.costoMinimo;
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
public class Lab3Test {
    
    public Lab3Test() {
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
     * Test of nReinas method, of class Lab3.
     */
    @Test
    public void testNReinas() {
        System.out.println("nReinas");
        int n = 4;
        int[] expResult = {1,3,0,2};
        int[] result = Lab3.nReinas(n);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of recorridoBfs method, of class Lab3.
     */
    @Test
    public void testRecorridoBfs() {
        System.out.println("recorridoBfs");
        Digraph g = new DigraphAL(8);
		g.addArc(1, 5, 10);
		g.addArc(0, 1, 20);
		g.addArc(4, 1, 50);
		g.addArc(4, 6, 30);
		g.addArc(0, 6, 90);
		g.addArc(6, 0, 20);
		g.addArc(0, 3, 80);
		g.addArc(5, 3, 40);
		g.addArc(3, 6, 20);
		g.addArc(5, 2, 10);
		g.addArc(2, 5, 50);
		g.addArc(3, 2, 10);
		g.addArc(2, 3, 10);
		g.addArc(2, 7, 20);
        int start = 0;
        ArrayList<Integer> expResult = new ArrayList<>();
        expResult.add(0);
        expResult.add(1);
        expResult.add(3);
        expResult.add(6);
        expResult.add(5);
        expResult.add(2);
        expResult.add(7);
        ArrayList<Integer> result = Lab3.recorridoBfs(g, start);
        assertEquals(expResult, result);
    }

    /**
     * Test of caminoCorto method, of class Lab3.
     */
    @Test
    public void testCaminoCorto() {
        System.out.println("caminoCorto");
        Digraph g = new DigraphAL(4);
        g.addArc(0, 1, 7);
        g.addArc(0, 2, 15);
        g.addArc(0, 3, 6);
        g.addArc(1, 0, 2);
        g.addArc(1, 2, 7);
        g.addArc(1, 3, 3);
        g.addArc(2, 0, 9);
        g.addArc(2, 1, 6);
        g.addArc(2, 3, 12);
        g.addArc(3, 0, 10);
        g.addArc(3, 1, 4);
        g.addArc(3, 2, 8);
        int inicio = 0;
        int fin = 3;
        ArrayList<Integer> expResult = new ArrayList<>();
        expResult.add(0);
        ArrayList<Integer> result = costoMinimo(g, inicio, fin);

    }

    /**
     * Test of lcs method, of class Lab3.
     */
    @Test
    public void testLcs() {
        System.out.println("lcs");
        String s1 = "ABCDGH";
        String s2 = "AEDFHR";
        int expResult = 3;
        int result = Lab3.lcs(s1, s2);
        assertEquals(expResult, result);
    }
    
}
