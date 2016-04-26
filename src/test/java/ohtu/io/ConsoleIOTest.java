/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

/**
 *
 * @author susisusi
 */
public class ConsoleIOTest {

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    ConsoleIO consoleIO;

    @Before
    public void setUp() {
        Scanner scanner = new Scanner(System.in);
        consoleIO = new ConsoleIO(scanner);
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void readLineCorrectly() {
        systemInMock.provideLines("testt");
        assertEquals("testt", consoleIO.readLine("1"));
    }

    @Test
    public void readIntCorrectly() {
        systemInMock.provideLines("2");
        assertEquals(2, consoleIO.readInt("1"));
    }
    
    @Test
    public void printsCorrectly() {
        consoleIO.print("tidii");
         assertEquals("tidii\n", outContent.toString());
    }
}
