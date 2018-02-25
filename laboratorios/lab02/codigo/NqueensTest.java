/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2;

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
public class NqueensTest {
    
    public NqueensTest() {
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
     * Test of esValido method, of class Nqueens.
     */
    @Test
    public void testEsValido() {
        System.out.println("esValido");
        Integer[] tablero = {0,1,2,3};
        boolean expResult = false;
        boolean result = Nqueens.esValido(tablero);
        assertEquals(expResult, result);
        Integer[] tablero2 = {1,3,0,2};
        boolean expResult2 = true;
        boolean result2 = Nqueens.esValido(tablero2);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of queens method, of class Nqueens.
     */
    @Test
    public void testQueens() {
        System.out.println("queens");
        int n1 = 4;
        int expResult1 = 2;
        int result1 = Nqueens.queens(n1);
        assertEquals(expResult1, result1, 0);
        int n2 = 5;
        int expResult2 = 10;
        int result2 = Nqueens.queens(n2);
        assertEquals(expResult2, result2, 0);
    }
    
}
