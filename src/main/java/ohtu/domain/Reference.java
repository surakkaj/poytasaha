/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Daniel Viktor Isaac
 */
public abstract class Reference {

    /**
     * First string is the tag, second is the content ie: {author:sokrates} or
     * {year:1337}
     */
    public Map<String, String> tags;
    private String citationKey;
    private Set<String> requiredFields;
    private Set<String> optionalFields;

    public Reference(String ck) {
        this.citationKey = ck;
        this.tags = new HashMap<String, String>();
    }

    public void addRequirements(String[] rf, String[] of) {
        this.requiredFields = new HashSet<String>(Arrays.asList(rf));
        this.optionalFields = new HashSet<String>(Arrays.asList(of));
    }
    
    public boolean addFromHashMap(HashMap<String, String> map) {
        for(String req : map.keySet()){
            this.addTag(req, map.get(req));
            
            
        }
        return true;
    }

    public Boolean addTag(String tag, String content) {
        if (tag == null || content == null) {
            return false;
        }
        if (!validate(tag)) {
            return false;
        }
        if (this.tags.containsKey(tag)) {
            return false;
        }
        this.tags.put(tag, content);
        return true;
    }

    private Boolean validate(String tag) {
        if (this.requiredFields.contains(tag) || this.optionalFields.contains(tag)) {
            return true;
        }
        return false;
    }

    public String toBibtex() {
        String pre = "{ " + citationKey + ",\n";
        StringBuilder sb = new StringBuilder(pre);
        for (String s : tags.keySet()) {
            String toAppend = "  " + s + " = {" + tags.get(s) + "},\n";
            sb.append(toAppend);
        }
        sb.replace(sb.length() - 2, sb.length() - 1, "");
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(citationKey + ":\n");
        for (String s : tags.keySet()) {
            String toAppend = s + ":" + tags.get(s) + "\n";
            sb.append(toAppend);
        }
        return sb.toString();
    }

    public String getCk() {
        return this.citationKey;
    }

    private void setFields(String[] ff, String[] of) {
        this.optionalFields = new HashSet<String>();
    }

}
