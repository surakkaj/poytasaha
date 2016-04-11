/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

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
        @Autowired
        public Poytasaha(IO io){
            this.io = io;
        }
        public void run() {
        while (true) {
            String command = io.readLine(">");

            if (command.isEmpty()) {
                break;
            }

            if (command.equals("add")) {
                io.print("ADD");
            } else if (command.equals("delete")) {
                io.print("DELETE");
            }

        }
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
