/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.io;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author susisusi
 */
public class StubIOTest {

    StubIO stubIO;

    @Before
    public void setUp() {
        stubIO = new StubIO("1");
    }

    @Test
    public void printsCorrectly() {
        stubIO.print("tidii");
        assertEquals(1, stubIO.getPrints().size());
    }

    @Test
    public void readsIntCorrectly() {
        assertEquals(1, stubIO.readInt("2"));
    }

    @Test
    public void readLinesCorrectly() {
        assertEquals("1", stubIO.readLine("1"));
        assertEquals("", stubIO.readLine("1"));
        assertEquals("", stubIO.readLine("3"));
    }

}
