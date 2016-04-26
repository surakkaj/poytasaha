/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.util.Scanner;
import ohtu.UI.UI;
import ohtu.data_access.FileReferenceDao;
import ohtu.io.ConsoleIO;
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

//    private IO io;
//    private FileIO fio;
//
//    @Autowired
//    public Poytasaha(IO io, FileIO fio) {
//        this.io = io;
//        this.fio = fio;
//    }

//    public void run() {
//        StringBuilder memory = new StringBuilder("");
//        while (true) {
//            String command = io.readLine(">");
//
//            if (command.isEmpty()) {
//                break;
//            }
//
//            if (command.equals("add")) {
//                io.print("Add lines to memory: empty breaks");
//                while (true) {
//                    command = io.readLine(">") + "\n";
//                    memory.append(command);
//                    if (command.equals("\n")) {
//                        command = "1";
//                        break;
//                    }
//                    io.print(memory.toString());
//                }
//            } else if (command.equals("delete")) {
//                io.print("DELETED MEMORY");
//
//                memory.replace(0, memory.length() - 1, "");
//
//            } else if (command.equals("load")) {
//                io.print("load");
//            } else if (command.equals("save")) {
//                fio.print(memory.toString());
//                fio.print("SAVE");
//                memory.replace(0, memory.length() - 1, "");
//            } else if (command.equals("sout")) {
//                io.print(memory.toString());
//            }
//
//        }
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        // TODO code application logic here
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");
//        Poytasaha poytasaha = (Poytasaha) ctx.getBean("poytasaha");
//        poytasaha.run();

//        Scanner lukija = new Scanner(System.in);
        ConsoleIO io = new ConsoleIO(new Scanner(System.in));
        FileReferenceDao dao = new FileReferenceDao();
        UI ui = new UI(io, dao);
       
        ui.run();
        
        
    }

}
