/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import ohtu.domain.Article;
import ohtu.domain.Book;
import ohtu.domain.Inproceedings;
import ohtu.domain.Manual;
import ohtu.domain.Reference;
import ohtu.io.FileIO;
import ohtu.io.FileIOInterface;

/**
 *
 * @author Daniel Viktor Isaac
 */
public class FileReferenceDao implements ReferenceDao {

    private List<Reference> list;
    private FileIOInterface io;

    public FileReferenceDao() {
        this.list = new ArrayList<Reference>();
        this.io = new FileIO();
    }
    
    public FileReferenceDao(FileIOInterface io){
        this.list = new ArrayList<Reference>();
        this.io = io;
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
        } else if(map.containsKey("manual")){
            ref = new Manual (map.get("manual"));
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
    
    public String giveAllReferencesFromOneFormat(String format) {
        ArrayList<Reference> result = new ArrayList<Reference>();
        for (Reference ref : this.list) {
            if (ref.getType().equals(format)) {
                result.add(ref);
            }
        }
        StringBuilder sb = new StringBuilder("");
        for (Reference r : result) {
            sb.append(r.toBibtex());
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /**
     * this method splits a bibtex file from the @ signs and sends them through the "parseReference" method, and adds them to the list one reference at a thime
     * @param Filepath the path of the file
     */
    public void loadfromBibtex(String Filepath) {
        String file = io.readFile(Filepath);
        List<String> items = Arrays.asList(file.split("@"));
        Reference ref = null;
        for (String s : items) {
            if (s.length() <= 4) {
                continue;
            }
            ref = parseReference(s);
            this.list.add(ref);
        }
    }
/**
 * 
 * @param a the first character of the referencetype
 * @param ck citation key
 * @return a newly formed reference in the desired format
 */
  public static Reference selectType(char a, String ck) {
        System.out.println(a);
        if (a == 'a') {
            return new Article(ck);
        }
        if (a == 'b') {
            return new Book(ck);

        }
        if (a == 'i') {
            return new Inproceedings(ck);

        }
        if (a == 'm') {
            return new Manual(ck);

        }
        return null;
    }
/**
 * 
 * @param context the of the bibtext without the @
 * @return the reference in the reference form
 */
  public static Reference parseReference(String context) {

        List<String> items = Arrays.asList(context.split("\\n"));

        String[] slot = items.get(0).split("\\{");

        slot[1] = slot[1].substring(0, slot[1].length() - 2);
        Reference ref = selectType(slot[0].charAt(0), slot[1]);
        if (ref == null) {
            return null;
        }
        items = items.subList(1, items.size() - 1);
        // withouth this the last entry's last character will be removed.
        items.set(items.size() - 2, items.get(items.size() - 1) + ".");
        for (String s : items) {
            if (!s.contains("=")) {
                continue;
            }
            slot = s.split("=");
            slot[0] = slot[0].replaceAll("[^a-zA-Z0-9]+", "");
            slot[1] = slot[1].substring(2, slot[1].length() - 3);
            if (ref.addTag(slot[0], slot[1])) {

            } else {
                System.out.println("could not add: " + slot[0]);
            }

        }

        return ref;
    }

}
