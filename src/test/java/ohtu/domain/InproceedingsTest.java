/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class InproceedingsTest {

    Inproceedings inproceedings;

    @Before
    public void setUp() {
        inproceedings = new Inproceedings("Test");
    }

    @Test
    public void tagIsAddedCorrectly() {
        assertEquals(true, inproceedings.addTag("author", "Aleksis Kivi"));
        assertEquals(true, inproceedings.addTag("year", "1850"));
        assertEquals(true, inproceedings.addTag("publisher", "Otava"));
    }

    @Test
    public void tagIsAddedOnce() {
        assertEquals(true, inproceedings.addTag("author", "Aleksis Kivi"));
        assertEquals(false, inproceedings.addTag("author", "Aleksis Kivi"));
        assertEquals(true, inproceedings.addTag("publisher", "Otava"));
        assertEquals(false, inproceedings.addTag("publisher", "Otava Media"));
    }

    @Test
    public void invalidTagIsNotAdded() {
        assertEquals(false, inproceedings.addTag("kirjailija", "Aleksis Kivi"));
        assertEquals(false, inproceedings.addTag("edition", "22"));
    }

    @Test
    public void requirementsAreAddedCorrectly() {
        assertEquals(4, inproceedings.FORCE_FIELDS.length);
        assertEquals(10, inproceedings.OPTIONAL_FIELDS.length);
    }

    @Test
    public void bibtextIsPrintedCorrectly() {
        FileReferenceDao dao = new FileReferenceDao();
        inproceedings.addTag("author", "Aleksis Kivi");
        inproceedings.addTag("publisher", "Otava");
        dao.add(inproceedings);
        Assert.assertTrue(dao.toBibtex().contains("@inproceedings {"));
        Assert.assertTrue(dao.toBibtex().contains("author = {Aleksis Kivi}"));  
        Assert.assertTrue(dao.toBibtex().contains("publisher = {Otava}"));  
    }

    @Test
    public void returnsTypeCorrectly() {
        assertEquals("inproceedings", inproceedings.getType());
    }
}
