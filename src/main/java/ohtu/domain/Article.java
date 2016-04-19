/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domain;

/**
 *
 * @author Daniel Viktor Isaac
 */
public class Article extends Reference {
    final String[] FORCE_FIELDS = {"author", "title", "journal", "year"};
    final String[] OPTIONAL_FIELDS = {"volume", "number", "pages", "month", "note", "key"};
    public Article(String ck) {
        super(ck);
        super.addRequirements(FORCE_FIELDS, OPTIONAL_FIELDS);
        
    }
    
    @Override
    public String toBibtex() {
        return "@article " + super.toBibtex();
    }
    
}