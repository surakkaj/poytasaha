/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.util.*;
import ohtu.domain.Article;
import ohtu.domain.Book;
import ohtu.domain.Inproceedings;
import ohtu.domain.Reference;
import ohtu.io.FileIO;
import org.junit.Assert;
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
    Reference inproceedings;
    FileIO io;
    HashMap<String, String> map;

    @Before
    public void setUp() {
        book = new Book("book");
        article = new Article("article");
        inproceedings = new Inproceedings("inproceedings");
        list = new FileReferenceDao();
        list.add(book);
        list.add(article);
        list.add(inproceedings);
        map = new HashMap<String, String>();
    }

    @Test
    public void listsAllReferences() {
        assertEquals(3, list.listAll().size());
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

    @Test
    public void addsCorrectlyHashMap() {
        map.put("book", null);
        map.put("article", null);
        map.put("inproceedings", null);
        list.add(map);
        assertEquals(3, map.size());
    }

    @Test
    public void addsArticleInHashMap() {
        map.put("article", null);
        list.add(map);
        assertEquals(true, list.listAll().contains(article));
    }

    @Test
    public void addsBookInHashMap() {
        map.put("book", null);
        list.add(map);
        assertEquals(true, list.listAll().contains(book));
    }

    @Test
    public void addsInproceedingsInHashMap() {
        map.put("inproceedings", null);
        list.add(map);
        assertEquals(true, list.listAll().contains(inproceedings));
    }
}
