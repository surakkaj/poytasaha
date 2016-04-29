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
    public void testWrite() {
        String fName = "testausta.txt";
        fio.write(fName, "testaa testaa");
        fio.write(fName, "kissa koira");
        String result = fio.readFile(fName);
        Assert.assertTrue(result.contains("testaa"));
        Assert.assertTrue(result.contains("koira"));
        File f = new File(fName);
        f.delete();
    }

    @Test
    public void testSpecialChars() {       
        String text = "ÅåÄäÖö";
        String result = fio.replaceSpecialChars(text);
        String expected = "\\AA\\aa{\\\"A}{\\\"a}{\\\"O}{\\\"o}";
        Assert.assertEquals(expected,result);
        text = "Üüß";
        result = fio.replaceSpecialChars(text);
        expected = "{\\\"U}{\\\"u}\\ss";
        Assert.assertEquals(expected,result);
        text = "ÆæØø";
        result = fio.replaceSpecialChars(text);
        expected = "\\AE\\ae\\O\\o";
        Assert.assertEquals(expected,result);        

        Assert.assertEquals("ÅåÄäÖö", fio.replaceBibtexFormatChars("\\AA\\aa{\\\"A}{\\\"a}{\\\"O}{\\\"o}"));
        Assert.assertEquals("Üüß", fio.replaceBibtexFormatChars("{\\\"U}{\\\"u}\\ss"));
        Assert.assertEquals("ÆæØø", fio.replaceBibtexFormatChars("\\AE\\ae\\O\\o"));         
    }    

    @Test
    public void testReadFile() {
        String fName = "testausta.txt";
        fio.write(fName, "testaa testaa");
        String result = fio.readFile(fName);
        Assert.assertTrue(result.contains("testaa testaa"));
        File f = new File(fName);
        f.delete();
    }

}
