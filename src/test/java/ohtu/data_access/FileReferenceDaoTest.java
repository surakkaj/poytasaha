/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import ohtu.domain.Article;
import ohtu.domain.Book;
import ohtu.domain.Reference;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author susisusi
 */
public class FileReferenceDaoTest {

    FileReferenceDao list;
    Reference book;
    Reference article;

    @Before
    public void setUp() {
        book = new Book("book");
        article = new Article("article");
        list = new FileReferenceDao();
        list.add(book);
        list.add(article);
    }

    @Test
    public void listsAllReferences() {
        assertEquals(2, list.listAll().size());
    }

    @Test
    public void searchCitationKeyCorrectly() {
        assertEquals(article, list.searchByCitationKey("article"));
        assertEquals(book, list.searchByCitationKey("book"));
    }

    @Test
    public void returnsNullIfInvalidCitationKey() {
        assertEquals(null, list.searchByCitationKey(""));
        assertEquals(null, list.searchByCitationKey("movie"));
    }
}
