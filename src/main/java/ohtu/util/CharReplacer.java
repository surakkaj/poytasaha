package ohtu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for replacing some special characters
 * 
 */
public class CharReplacer {
    /**
     * Replaces some special characters to be more compatible with bibtex-format
     * 
     * @param text String original text
     * @return String text where special characters have been replaced
     */
    public static String replaceSpecialChars(String text){
        String str = text;
        str = str.replace("å","\\aa");
        str = str.replace("Å","\\AA");        
        str = str.replace("ä","{\\\"a}");
        str = str.replace("Ä","{\\\"A}");
        str = str.replace("ö","{\\\"o}");
        str = str.replace("Ö","{\\\"O}");
        str = str.replace("ü","{\\\"u}"); 
        str = str.replace("Ü","{\\\"U}");
        str = str.replace("ß","\\ss");
        str = str.replace("æ","\\ae");
        str = str.replace("Æ","\\AE");
        str = str.replace("ø","\\o");
        str = str.replace("Ø","\\O");
        return str;        
    }
    
    /**
     * Replaces some special characters from bibtex compatible format to normal
     * 
     * @param text String in bibtex compatible format
     * @return String normal text
     */
    public static String replaceBibtexFormatChars(String text){
        String str = text;
        if(str.contains("\\")){
            str = str.replace("\\aa","å");
            str = str.replace("\\AA","Å");        
            str = str.replace("{\\\"a}","ä");
            str = str.replace("{\\\"A}","Ä");
            str = str.replace("{\\\"o}","ö");
            str = str.replace("{\\\"O}","Ö");
            str = str.replace("{\\\"u}","ü"); 
            str = str.replace("{\\\"U}","Ü");
            str = str.replace("\\ss","ß");
            str = str.replace("\\ae","æ");
            str = str.replace("\\AE","Æ");
            str = str.replace("\\o","ø");
            str = str.replace("\\O","Ø");
        }
        return str;        
    }     
}
