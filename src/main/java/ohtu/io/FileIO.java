package ohtu.io;

import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
    
    private String filePath;
    
    public FileIO(String filePath){
       this.filePath = filePath; 
    }
    
    public void writeToFile (String text){

        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error:" + e.getMessage());
        } 
    }
    
}
