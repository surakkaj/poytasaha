package ohtu.io;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileIOTest {

    private FileIO fio;

    public FileIOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
        fio = new FileIO();
    }

    @Test
    public void testPrint() {
        fio.setFilePath("testausta2.txt");
        fio.print("koira kissa hiiri");
        String result = fio.readFile(fio.getFilePath());
        Assert.assertTrue(result.contains("hiiri"));
        File f = new File(fio.getFilePath());
        f.delete();
    }

//
//    @Test
//    public void testGetFilePath() {
//    }
//
//    @Test
//    public void testSetFilePath() {
//    }
    @Test
    public void testWrite() {
        String fName = "testausta.txt";
        fio.write(fName, "testaa testaa");
        String result = fio.readFile(fName);
        Assert.assertTrue(result.contains("testaa"));
        File f = new File(fName);
        f.delete();
    }

    @Test
    public void testOverwrite() {
        String fName = "testausta.txt";
        fio.write(fName, "testaa testaa");
        fio.overwrite(fName, "koira kissa hiiri");
        String result = fio.readFile(fName);
        Assert.assertTrue(!result.contains("testaa"));
        Assert.assertTrue(result.contains("hiiri"));
        File f = new File(fName);
        f.delete();
    }

    @Test
    public void testReadFile() {
        String fName = "testausta.txt";
        fio.write(fName, "testaa testaa");
        String result = fio.readFile(fName);
        Assert.assertTrue(result.contains("testaa"));
        File f = new File(fName);
        f.delete();
    }

}
