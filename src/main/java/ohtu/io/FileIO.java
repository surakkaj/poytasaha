package ohtu.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 * Class for reading and writing text files.
 */
@Component
public class FileIO {
    
    private String filePath;
    
    public FileIO(){
        this.filePath = "";
    }
  
    /**
     * Writes the given string to the end of the given file.
     * 
     * If the file doesn't exist, it will be created.
     * 
     * @param fileName  
     * @param text String to be written
     */
    public void write(String fileName, String text){
        this.filePath = fileName;
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(this.replaceSpecialChars(text));
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error:" + e.getMessage());
        } 
    }      

    /**
     * Reads a textfile and returns its contents as a String.
     * 
     * @param fileName  file to be read
     * @return  file's content as a String
     */
    public String readFile(String fileName) {
        this.filePath = fileName;
        String all = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("There was an error:" + e.getMessage());
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while(line != null){
                sb.append(line);
                sb.append(System.lineSeparator()); 
                line = reader.readLine();
            }
            all = sb.toString();
            reader.close();
        } catch (IOException e) {
            System.out.println("There was an error:" + e.getMessage());
        }
        return replaceBibtexFormatChars(all);
    }
    
    /**
     * Replaces some special characters to be more compatible with bibtex-format
     * 
     * @param text String original text
     * @return String text where special characters have been replaced
     */
    public String replaceSpecialChars(String text){
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
    public String replaceBibtexFormatChars(String text){
        String str = text;
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
        return str;        
    } 
}