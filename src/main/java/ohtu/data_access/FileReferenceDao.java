/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.util.ArrayList;
import java.util.List;
import ohtu.domain.Reference;

/**
 *
 * @author Daniel Viktor Isaac
 */
public class FileReferenceDao implements ReferenceDao {

    private List<Reference> list;

    public FileReferenceDao() {
        this.list = new ArrayList<Reference>();
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
    
    public String toBibtex(){
        StringBuilder sb = new StringBuilder("");
        for (Reference r : this.list) {
            sb.append(r.toBibtex());
            sb.append("\n");
        }
        return sb.toString();
    }

}
