package ohtu.io;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileIOTest {

    private FileIO fio;
    String fName = "target/testausta.txt";

    public FileIOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
        fio = new FileIO();
        File temp = new File(fName); 
        if (temp.exists()) {
          temp.delete();
        }    
    }
    
    @After
    public void deleteTestFile() {
        File temp = new File(fName); 
        if (temp.exists()) {
          temp.delete();
        }  
    }
    
    @Test
    public void testWrite() {
        fio.write(fName, "testaa testaa");
        fio.write(fName, "kissa koira");
        String result = fio.readFile(fName);
        Assert.assertTrue(result.contains("testaa"));
        Assert.assertTrue(result.contains("koira"));
    }

    @Test
    public void testSpecialChars() { 
        
        Assert.assertEquals("\\AA\\aa{\\\"A}{\\\"a}{\\\"O}{\\\"o}",fio.replaceSpecialChars("ÅåÄäÖö"));
        Assert.assertEquals("{\\\"U}{\\\"u}\\ss",fio.replaceSpecialChars("Üüß"));
        Assert.assertEquals("\\AE\\ae\\O\\o",fio.replaceSpecialChars("ÆæØø"));      

        Assert.assertEquals("ÅåÄäÖö", fio.replaceBibtexFormatChars("\\AA\\aa{\\\"A}{\\\"a}{\\\"O}{\\\"o}"));
        Assert.assertEquals("Üüß", fio.replaceBibtexFormatChars("{\\\"U}{\\\"u}\\ss"));
        Assert.assertEquals("ÆæØø", fio.replaceBibtexFormatChars("\\AE\\ae\\O\\o"));         
    }    

    @Test
    public void testReadFile() {
        fio.write(fName, "hiiri hilleri");
        String result = fio.readFile(fName);
        Assert.assertTrue(result.contains("hiiri hilleri"));
    }

}
