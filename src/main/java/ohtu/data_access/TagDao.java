/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.util.List;
import ohtu.domain.Tag;

/**
 *
 * @author Daniel Viktor Isaac
 */
public interface TagDao {
    /** @return a list of all the Tags in this dao */
    List<Tag> listAll();
    /** @param tag tag to be added to this dao */
    void add(Tag tag);
    
    void searchByContext();
    void searchById();
}