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
import ohtu.domain.Manual;
import ohtu.domain.Reference;
import ohtu.io.FileIO;
import ohtu.io.FileIOInterface;
import ohtu.io.StubFileIO;
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
    Reference manual;
    FileIO io;
    HashMap<String, String> map;
    FileReferenceDao frd;

    @Before
    public void setUp() {
        book = new Book("book");
        article = new Article("article");
        inproceedings = new Inproceedings("inproceedings");
        manual = new Manual("manual");
        list = new FileReferenceDao();
        list.add(book);
        list.add(article);
        list.add(inproceedings);
        list.add(manual);
        map = new HashMap<String, String>();
        frd = new FileReferenceDao(new StubFileIO());
    }

    @Test
    public void listsAllReferences() {
        assertEquals(4, list.listAll().size());
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
    public void addCorrectlyHashMap() {
        map.put("book", null);
        map.put("article", null);
        map.put("inproceedings", null);
        map.put("manual", null);
        list.add(map);
        assertEquals(4, map.size());
    }

    @Test
    public void addArticleInHashMap() {
        map.put("article", null);
        list.add(map);
        assertEquals(true, list.listAll().contains(article));
    }

    @Test
    public void addBookInHashMap() {
        map.put("book", null);
        list.add(map);
        assertEquals(true, list.listAll().contains(book));
    }

    @Test
    public void addInproceedingsInHashMap() {
        map.put("inproceedings", null);
        list.add(map);
        assertEquals(true, list.listAll().contains(inproceedings));
    }

    @Test
    public void addManualInHashMap() {
        map.put("manual", null);
        list.add(map);
        assertEquals(true, list.listAll().contains(manual));
    }

    @Test
    public void giveAllReferencesFromOneFormatCorrectly() {
        Assert.assertTrue(list.giveAllReferencesFromOneFormat("article").contains("@article { article"));
    }

    @Test
    public void selectTypeCorrectly() {
        assertEquals("article", list.selectType('a', "ar").getType());
        assertEquals("book", list.selectType('b', "b").getType());
        assertEquals("inproceedings", list.selectType('i', "i").getType());
        assertEquals("manual", list.selectType('m', "m").getType());
    }

    @Test
    public void returnNullWhenCharIsInvalid() {
        assertEquals(null, list.selectType('w', "ar"));
    }

    @Test
    public void parseReferenceTest() {
        String context = "article { Test,\n"
                + "  author = {Aleksis Kivi},\n"
                + "  title = {Kullervo},\n"
                + "  pages : {320}\n"
                + "}\n\n";
        Assert.assertTrue(list.parseReference(context).getTags().containsKey("author"));
    }

    @Test
    public void parseReferenceTestMethodReturnsNull() {
        String context = "@article { Test,\n"
                + "  author = {Aleksis Kivi},\n"
                + "  title = {Kullervo},\n"
                + "  pages = {320}\n"
                + "}\n\n";
        assertEquals(null, list.parseReference(context));
    }
}
