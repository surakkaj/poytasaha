package ohtu.util;

import static ohtu.util.CharReplacer.replaceBibtexFormatChars;
import static ohtu.util.CharReplacer.replaceSpecialChars;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anu
 */
public class CharReplacerTest {
    
    public CharReplacerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void testReplaceSpecialChars() {        
        Assert.assertEquals("\\AA\\aa{\\\"A}{\\\"a}{\\\"O}{\\\"o}",replaceSpecialChars("ÅåÄäÖö"));
        Assert.assertEquals("{\\\"U}{\\\"u}\\ss",replaceSpecialChars("Üüß"));
        Assert.assertEquals("\\AE\\ae\\O\\o",replaceSpecialChars("ÆæØø"));        
    }

    @Test
    public void testReplaceBibtexFormatChars() {
        Assert.assertEquals("ÅåÄäÖö", replaceBibtexFormatChars("\\AA\\aa{\\\"A}{\\\"a}{\\\"O}{\\\"o}"));
        Assert.assertEquals("Üüß", replaceBibtexFormatChars("{\\\"U}{\\\"u}\\ss"));
        Assert.assertEquals("ÆæØø", replaceBibtexFormatChars("\\AE\\ae\\O\\o"));         
    }
    
}
