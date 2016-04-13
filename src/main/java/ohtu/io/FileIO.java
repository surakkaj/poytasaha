/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.io;

import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
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
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error:" + e.getMessage());
        } 
    }

    
    public int readInt(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public String readLine(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
