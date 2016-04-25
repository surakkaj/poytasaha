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

public class Book extends Reference {

    final String[] FORCE_FIELDS = {"author", "title", "publisher", "year"};
    final String[] OPTIONAL_FIELDS = {"volume", "series", "address", "edition", "month", "note", "key"};

    public Book(String ck) {
        super(ck);
        super.addRequirements(FORCE_FIELDS, OPTIONAL_FIELDS);
    }

    @Override
    public String toBibtex() {
        return "@book " + super.toBibtex();
    }
    
    @Override
    public String getType() {
        return "book";
    }

}

