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

    public FileIO() {
        this.filePath = "testi.txt";
    }

    /**
     * Prints text to currently chosen file.
     * 
     * @param text 
     */
    public void print(String text) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error:" + e.getMessage());
        } 
    }

    /**
     * Returns the current filepath
     * 
     * @return filepath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets a new filepath
     * 
     * @param filePath 
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error:" + e.getMessage());
        } 
    }  
    
    
    /**
     * Writes the given string to the given file.
     * 
     * Overwrites the existing content.
     * If the file doesn't exist, it will be created.
     * 
     * @param fileName  
     * @param text String to be written
     */
    public void overwrite(String fileName, String text){

        try {
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(text);
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
        String all = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("There was an error:" + e.getMessage());
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while(line != null){
                sb.append(line);
                sb.append(System.lineSeparator());   //comment/delete if not useful
                line = reader.readLine();
            }
            all = sb.toString();
            reader.close();
        } catch (IOException e) {
            System.out.println("There was an error:" + e.getMessage());
        }
        return all;
    }

}
