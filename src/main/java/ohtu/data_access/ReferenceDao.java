/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.util.HashMap;
import java.util.List;
import ohtu.domain.Reference;

/**
 *
 * @author Daniel Viktor Isaac
 */
public interface ReferenceDao {
    
    /** @return a list of all the Tags in this dao */
    List<Reference> listAll();
    /** @param tag tag to be added to this dao */
    void add(Reference tag);
    void add(HashMap<String, String> map);
    
    Reference searchByContext(String c);
    Reference searchByCitationKey(String ck);
}