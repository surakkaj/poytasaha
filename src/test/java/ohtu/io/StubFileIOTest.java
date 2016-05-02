package ohtu.io;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author susisusi
 */
public class StubFileIOTest {

    StubFileIO stubFileIO;

    @Before
    public void setUp() {
        stubFileIO = new StubFileIO();
    }

    @Test
    public void returnsEmptyStringIfFileNameDoesntMatchWithFilePath() {
        assertEquals("", stubFileIO.readFile("eiTasmaa"));
    }

    @Test
    public void writeFileCorrectly() {
        stubFileIO.write("testi.txt", "tidii");
        assertEquals("tidii", stubFileIO.readFile("testi.txt"));
    }
}
