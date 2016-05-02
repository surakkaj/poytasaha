package ohtu.domain;

import ohtu.data_access.FileReferenceDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author susisusi
 */
public class ManualTest {

    Manual manual;

    @Before
    public void setUp() {
        manual = new Manual("Test");
    }

    @Test
    public void requirementsAreAddedCorrectly() {
        assertEquals(1, manual.FORCE_FIELDS.length);
        assertEquals(8, manual.OPTIONAL_FIELDS.length);
    }

    @Test
    public void returnsTypeCorrectly() {
        assertEquals("manual", manual.getType());
    }

    @Test
    public void bibtextIsPrintedCorrectly() {
        FileReferenceDao dao = new FileReferenceDao();
        manual.addTag("title", "Test");
        manual.addTag("author", "Aleksis Kivi");    
        dao.add(manual);
        Assert.assertTrue(dao.toBibtex().contains("@manual {"));
        Assert.assertTrue(dao.toBibtex().contains("title = {Test}"));
        Assert.assertTrue(dao.toBibtex().contains("author = {Aleksis Kivi}"));    
    }
}
