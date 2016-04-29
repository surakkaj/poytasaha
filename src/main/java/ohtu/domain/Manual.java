/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domain;

/**
 *
 * @author melchan
 */
public class Manual extends Reference {

    final String[] FORCE_FIELDS = {"title"};
    final String[] OPTIONAL_FIELDS = {"author", "organization", "address", "edition", "month", "year", "note", "key"};

    public Manual(String ck) {
        super(ck);
        super.addRequirements(FORCE_FIELDS, OPTIONAL_FIELDS);
    }

    @Override
    public String toBibtex() {
        return "@manual " + super.toBibtex();
    }
    
    @Override
    public String getType() {
        return "manual";
    }
    
}
