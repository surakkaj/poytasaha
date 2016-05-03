package ohtu.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static ohtu.util.CharReplacer.*;
import org.springframework.stereotype.Component;

/**
 * Class for reading and writing text files.
 */
@Component
public class FileIO implements FileIOInterface {
    
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
    @Override
    public void write(String fileName, String text){
        this.filePath = fileName;
        try {
            FileWriter writer = new FileWriter(filePath, false);
            writer.write(replaceSpecialChars(text));
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
    @Override
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
}