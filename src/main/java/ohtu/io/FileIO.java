<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.io;

=======
package ohtu.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
>>>>>>> d015d04147d293690e562f6bc155ecefbfba3316
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
<<<<<<< HEAD
 *
 * @author Daniel Viktor Isaac
 */
@Component
public class FileIO {

    private String filePath;

    public FileIO() {
        this.filePath = "testi.txt";
    }

    
    public void print(String text) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(text + "\n");
=======
 * Class for reading and writing text files.
 */
@Component
public class FileIO {  
    
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
>>>>>>> d015d04147d293690e562f6bc155ecefbfba3316
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error:" + e.getMessage());
        } 
    }
<<<<<<< HEAD

    
    public int readInt(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public String readLine(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

=======
    
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
    
>>>>>>> d015d04147d293690e562f6bc155ecefbfba3316
}
