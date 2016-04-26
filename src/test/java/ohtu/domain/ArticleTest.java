/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domain;

import ohtu.data_access.FileReferenceDao;
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
        article.addTag("pages", "320");
        dao.add(article);
        assertEquals("@article { Test,\n"
                + "  author = {Aleksis Kivi},\n"
                + "  title = {Kullervo},\n"
                + "  pages = {320}\n"
                + "}\n\n", dao.toBibtex());
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
    public void returnsTypeCorrectly(){
        assertEquals("article", article.getType());
    } 
}
