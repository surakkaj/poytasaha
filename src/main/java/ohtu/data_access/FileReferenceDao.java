/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ohtu.domain.Article;
import ohtu.domain.Book;
import ohtu.domain.Inproceedings;
import ohtu.domain.Reference;
import ohtu.io.FileIO;

/**
 *
 * @author Daniel Viktor Isaac
 */
public class FileReferenceDao implements ReferenceDao {

    private List<Reference> list;
    private FileIO io;

    public FileReferenceDao() {
        this.list = new ArrayList<Reference>();
        this.io = new FileIO();
    }

    @Override
    public List<Reference> listAll() {
        return this.list;
    }

    @Override
    public void add(Reference ref) {
        this.list.add(ref);
    }

    @Override
    public void add(HashMap<String, String> map) {
        Reference ref = null;
        if (map.containsKey("book")) {
            ref = new Book(map.get("book"));
        } else if (map.containsKey("article")) {
            ref = new Article(map.get("article"));
        } else if (map.containsKey("inproceedings")) {
            ref = new Inproceedings(map.get("inproceedings"));
        }
        ref.addFromHashMap(map);
        this.add(ref);
    }

    @Override
    public Reference searchByContext(String c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reference searchByCitationKey(String ck) {
        for (Reference r : this.list) {
            if (r.getCk().equalsIgnoreCase(ck)) {
                return r;
            }
        }
        return null;
    }

    public String toBibtex() {
        StringBuilder sb = new StringBuilder("");
        for (Reference r : this.list) {
            sb.append(r.toBibtex());
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public void save(String filePath) {
        this.io.write(filePath, this.toBibtex());
    }

}
