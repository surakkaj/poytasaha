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

    public void loadFromBibtex(String FilePath) {
        String text = this.io.readFile(FilePath);
        text = text.replaceAll("\n*", "");
        //text = text.replaceAll("\\s*", "");
        System.out.println(text);
        Reference ref = new Book("placehoder");
        int i = 0;
        while (true) {
            if (text.charAt(i) == '@') {
                String ck = "";
                int k = 0;
                int im = 0;
                for (int j = 0; j <= 1; k++) {
                    if (text.charAt(i + k) == '{') {
                        j = 1;
                        k++;
                    }
                    if (text.charAt(i + k) == ',') {
                        j = 2;
                        im = k + 1;
                    }
                    if (j == 1) {
                        ck += text.charAt(i + k);
                    }

                }

                System.out.println(text);
                System.out.println(i);
                System.out.println(ck);
                System.out.println(text.charAt(i + 1));
                if (text.charAt(i + 1) == 'a') {
                    ref = new Article(ck);
                    System.out.println("ASDGDSAGDFSAFDSFASHFSHFSAHSAFDHSFAFSHAHSAFSAHF");
                } else if (text.charAt(i + 1) == 'b') {
                    ref = new Book(ck);
                } else if (text.charAt(i + 1) == 'i') {
                    ref = new Inproceedings(ck);
                }
                i += im;

                String tag = "";
                String content = "";
                int flag = 1;
                while (true) {

                    if (text.charAt(i) == '=') {
                        flag *= -1;
                        i++;
                    }
                    if (flag == 1) {
                        tag += text.charAt(i);
                    }
                    if (flag == -1) {
                        content += text.charAt(i);
                    }
                    if (text.charAt(i) == '}' && text.charAt(i + 1) == ',') {
                        System.out.println("t" + tag);
                        System.out.println("c" + content);

                        if (!tag.isEmpty() || !content.isEmpty() || ref != null) {
                            tag = tag.replaceAll("\\s*", "");
                            //tag = tag.replaceAll("", "");
                            
                            content = content.replaceAll("[\\{\\}]", "");
                            ref.addTag(tag, content);
                        }

                        tag = "";
                        content = "";
                        i += 1;
                        flag *= -1;
                    }
                    if (text.charAt(i) == '}' && text.charAt(i + 1) == '}') {
                        break;
                    }

                    i++;
                    if (i >= text.length()) {
                        break;
                    }
                }
            }
            if (ref != null) {
                this.list.add(ref);
                ref = null;
            }

            i++;
            if (i >= text.length()) {
                break;
            }
        }

    }

    public void save(String filePath) {
        this.io.write(filePath, this.toBibtex());
    }

}
