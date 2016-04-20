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
public class BookTest {

    Book book;

    @Before
    public void setUp() {
        book = new Book("Kirjani");
    }

    @Test
    public void tagIsAddedCorrectly() {
        assertEquals(true, book.addTag("author", "Aleksis Kivi"));
        assertEquals(true, book.addTag("year", "1850"));
        assertEquals(true, book.addTag("edition", "3"));
    }

    @Test
    public void tagIsAddedOnce() {
        assertEquals(true, book.addTag("author", "Aleksis Kivi"));
        assertEquals(false, book.addTag("author", "Aleksis Kivi"));
        assertEquals(true, book.addTag("edition", "2"));
        assertEquals(false, book.addTag("edition", "5"));
    }

    @Test
    public void invalidTagIsNotAdded() {
        assertEquals(false, book.addTag("kirjailija", "Aleksis Kivi"));
        assertEquals(false, book.addTag("pages", "22"));
    }

    @Test
    public void requirementsAreAddedCorrectly() {
        assertEquals(4, book.FORCE_FIELDS.length);
        assertEquals(7, book.OPTIONAL_FIELDS.length);
    }

    @Test
    public void bibtextIsPrintedCorrectly() {
        FileReferenceDao dao = new FileReferenceDao();
        book.addTag("author", "Aleksis Kivi");
        book.addTag("title", "Kullervo");
        book.addTag("edition", "3");
        dao.add(book);
        assertEquals("@book { Kirjani,\n"
                + "  edition = {3},\n"
                + "  author = {Aleksis Kivi},\n"          
                + "  title = {Kullervo}\n"
                + "}\n\n", dao.toBibtex());
    }

//    @Test
//    public void toStringIsPrintedCorrectly() {
//        Reference article2 = new Article("Hoi");
//        article2.addTag("author", "Aleksis Kivi");
//        article2.addTag("title", "Kullervo");
//        assertEquals("Hoi:\n"
//                + "author:Aleksis Kivi\n"
//                + "title:Kullervo\n", article2.toString());
//    }
}
