/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;
import ohtu.data_access.*;
import ohtu.domain.Article;
import ohtu.domain.Reference;
import ohtu.io.FileIO;
/**
 *
 * @author Daniel Viktor Isaac
 */
public class TestReferenceDao {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        FileReferenceDao dao = new FileReferenceDao();
//        Reference r = new Article("Andie33");
//        r.addTag("author", "Andie Kameli");
//        r.addTag("title", "kamelikamelikameli");
//        dao.add(r);
//        r = new Article("Kannustin");
//        r.addTag("author", "Kari sorsa");
//        r.addTag("auffthor", "Kasdsdri sorsa");
//        dao.add(r);
//        System.out.println(dao.toBibtex());
//        System.out.println("viii");
//        System.out.println(r.toString());
        
        FileIO io = new FileIO();
        io.write(null, "dff");
    }
    
}
