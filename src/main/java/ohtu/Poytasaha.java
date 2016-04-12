/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import ohtu.io.FileIO;
import ohtu.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel Viktor Isaac
 */
@Component
public class Poytasaha {

        private IO io;
        private FileIO fio;
        @Autowired
        public Poytasaha(IO io){
            this.io = io;
        }
        public void run() {
        while (true) {
            printInfo();
            String command = io.readLine(">");

            if (command.isEmpty()) {
                break;
            }

            if (command.equals("add")) {
                addReference();
            } else if (command.equals("delete")) {
                io.print("DELETE");
            }

        }
    }
        
        private void printInfo(){
            io.print("");
            io.print("Enter 'add' to add a reference");            
        }
        
        private void addReference(){
            io.print("Give the path and name of the file in which to write:");
            String path = io.readLine(">");
            fio = new FileIO(path);
            io.print("Give the reference you want to add");
            String reference = io.readLine(">");
            fio.writeToFile(reference); 
        }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");
        Poytasaha poytasaha = (Poytasaha) ctx.getBean("poytasaha");
        poytasaha.run();
    }
    
}
