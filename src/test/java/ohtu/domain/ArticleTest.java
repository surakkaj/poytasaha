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
public class ArticleTest {

    Article article;

    @Before
    public void setUp() {
        article = new Article("Test");
    }

    @Test
    public void tagIsAddedCorrectly() {
        assertEquals(true, article.addTag("author", "Aleksis Kivi"));
        assertEquals(true, article.addTag("year", "1850"));
        assertEquals(true, article.addTag("pages", "501"));
        assertEquals(3, article.getTags().size());
    }

    @Test
    public void tagIsAddedOnce() {
        assertEquals(true, article.addTag("author", "Aleksis Kivi"));
        assertEquals(false, article.addTag("author", "Aleksis Kivi"));
        assertEquals(true, article.addTag("pages", "501"));
        assertEquals(false, article.addTag("pages", "300"));
    }

    @Test
    public void invalidTagIsNotAdded() {
        assertEquals(false, article.addTag("kirjailija", "Aleksis Kivi"));
        assertEquals(false, article.addTag("series", "3"));
        assertEquals(0, article.getTags().size());
    }

    @Test
    public void requirementsAreAddedCorrectly() {
        assertEquals(4, article.FORCE_FIELDS.length);
        assertEquals(6, article.OPTIONAL_FIELDS.length);
    }

    @Test
    public void bibtextIsPrintedCorrectly() {
        FileReferenceDao dao = new FileReferenceDao();
        article.addTag("author", "Aleksis Kivi");
        article.addTag("title", "Kullervo");
        dao.add(article);
        Assert.assertTrue(dao.toBibtex().contains("@article {"));
        Assert.assertTrue(dao.toBibtex().contains("author = {Aleksis Kivi}"));
        Assert.assertTrue(dao.toBibtex().contains("title = {Kullervo}"));
    }

    @Test
    public void toStringIsPrintedCorrectly() {
        Reference article2 = new Article("Hoi");
        article2.addTag("author", "Aleksis Kivi");
        article2.addTag("title", "Kullervo");
        assertEquals("Hoi:\n"
                + "author:Aleksis Kivi\n"
                + "title:Kullervo\n", article2.toString());
    }

    @Test
    public void returnsTypeCorrectly() {
        assertEquals("article", article.getType());
    }
}
