/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ohtu.data_access.FileReferenceDao;
import ohtu.domain.Reference;
import ohtu.io.ConsoleIO;
import ohtu.io.IO;

/**
 *
 * @author melchan
 */
public class UI {
    
    private IO io;
    private FileReferenceDao dao;
    private boolean onSwitch;
    private Map<String, ArrayList<ArrayList<String>>> lists;
    
    public UI(IO io, FileReferenceDao dao) {
        this.io =io;
        this.dao = dao;
        this.onSwitch = true;
        createMapLists();
        
    }
    
    //koodia pitää muokata, jos haluaa nuo vaihtoehdon sisältävät tägit
    private void createMapLists() {
        this.lists = new HashMap<String, ArrayList<ArrayList<String>>>();
        String[] requiredArticle = {"author", "title", "journal", "year"};
        String[] optionalArticle = {"volume", "number", "pages", "month", "note", "key"};
        
        String[] requiredInproceedings = {"author", "title", "booktitle", "year"};
        String[] optionalInproceedings = {"editor", "volume", "series", "pages", "address", "month", "organization", "publisher", "note", "key"};
//        String[] optionalInproceedings = {"editor", "volume/number", "series", "pages", "address", "month", "organization", "publisher", "note", "key"};
        
        String[] requiredBook = {"author", "title", "publisher", "year"};
//        String[] requiredBook = {"author/editor", "title", "publisher", "year"};
        String[] optionalBook = {"volume", "series", "address", "edition", "month", "note", "key"};
//        String[] optionalBook = {"volume/number", "series", "address", "edition", "month", "note", "key"};
        
        String[] requiredManual = {"title"};
        String[] optionalManual = {"author", "organization", "address", "edition", "month", "year", "note", "key"};

        ArrayList<ArrayList<String>> article = new ArrayList<ArrayList<String>>();        
        ArrayList<String> required = new ArrayList<String>(Arrays.asList(requiredArticle));
        ArrayList<String> optional = new ArrayList<String>(Arrays.asList(optionalArticle));
        article.add(required);
        article.add(optional);        
        lists.put("article", article);
        
        ArrayList<ArrayList<String>> inproceedings = new ArrayList<ArrayList<String>>();
        required = new ArrayList<String>(Arrays.asList(requiredInproceedings));
        optional = new ArrayList<String>(Arrays.asList(optionalInproceedings));
        inproceedings.add(required);
        inproceedings.add(optional);        
        lists.put("inproceedings", inproceedings);
        
        ArrayList<ArrayList<String>> book = new ArrayList<ArrayList<String>>();        
        required = new ArrayList<String>(Arrays.asList(requiredBook));
        optional = new ArrayList<String>(Arrays.asList(optionalBook));
        book.add(required);
        book.add(optional);        
        lists.put("book", book);
        
        ArrayList<ArrayList<String>> manual = new ArrayList<ArrayList<String>>();        
        required = new ArrayList<String>(Arrays.asList(requiredManual));
        optional = new ArrayList<String>(Arrays.asList(optionalManual));
        manual.add(required);
        manual.add(optional);
        lists.put("manual", manual);
        
    }

    /**
     * Execute text ui.
     */
    public boolean run() {
        firstPhase();
        return onSwitch;
    }
    
    private void firstPhase() {
        startingInstructions();
        String input = io.readLine(">");
        while (fileIsRightFormat(input)) {
            wrongFileType();
            input = io.readLine(">");
        }
        while (this.onSwitch) {
            askWhatUserWantsToDo();
        }
        dao.save(input);
    }
    
    private boolean fileIsRightFormat(String input) {
        return !(input.endsWith(".txt") || input.endsWith(".bib"));
    }
    
    private void wrongFileType() {
        io.print("File didn't end in .txt or .bib");
    }
    
    private void startingInstructions() {
        //Kirjoitin souteilla että on helpompi lukuisempi
        io.print("Give file to write into. If file does not exist empty one will be created.");
        io.print("File must be in .txt or .bib format.");
        io.print("");
    }
    
    private void wrongInput() {
        io.print("Wrong input");
        io.print("");
    }
    
    private void askWhatUserWantsToDo() {
        instructUserForOptions();
        String input = io.readLine(">");
        input.replaceAll("\\s", "");
        if (input.equals("1")) {
            HashMap<String, String> info = createNewTagInfo();
            dao.add(info);
        } else if (input.equals("2")) {
            listContentOfFile();
        } else if (input.equals("3")) {
            lookUpExistingToModify();
        } else if (input.equals("4")) {
            removeExistingReference();
        } else if (input.equals("5")) {
            this.onSwitch = false;
        } else {
            wrongInput();
            askWhatUserWantsToDo();
        }
    }
    
    private void instructUserForOptions() {
        io.print("");
        io.print("Choose one number from below");
        io.print("(1) Create new reference");
        io.print("(2) List all existing references in file");
        io.print("(3) Choose existing reference to modify");
        io.print("(4) Choose existing reference to remove");
        io.print("(5) Save and quit");
    }
    
    private void lookUpExistingToModify() {
        io.print("");
        io.print("Give citation key of the reference you want to modify");
        io.print("");
        String input = io.readLine(">");
        Reference ref = dao.searchByCitationKey(input);
        io.print("");
        io.print(ref.toBibtex());
        io.print("");
        modifyReference(ref);
    }
    
    private void modifyReference(Reference ref) {
        HashMap<String, String> map = (HashMap) ref.getTags();
        ArrayList<ArrayList<String>> into = lists.get(ref.getType());
        askAndRecordToModify(map, into.get(0), into.get(1));
    }
    
    private void askAndRecordToModify(HashMap<String, String> result, List<String> required, List<String> optional) {
        io.print("");
        io.print("Fill required fields: ");
        
        for (String req : required) {
            String current = result.get(req);
            
            System.out.println("current " + req + " is " + current);
            askForRequiredInput(req, result);
        }
        io.print("");
        io.print("Fill optional fields: ");
        io.print("(you can skip field by pressing enter and keeping it empty)");
        
        for (String opt : optional) {
            String current = "";
            if (result.containsKey(opt)) {
                current = result.get(opt);
            }
            io.print("current " + opt + " is " + current);
            askForOptionalInput(opt, result);
        }
    }
    
    private void listContentOfFile() {
        io.print("");
        io.print("Listing content of file");
        io.print(dao.toBibtex());
    }
    
    private void removeExistingReference() {
        io.print("");
        io.print("give citationkey to remove ");
        io.print("");
        String input = io.readLine(">");
        Reference ref = dao.searchByCitationKey(input);
        if (ref != null) {
            dao.listAll().remove(ref);
        }
    }
    
//    private String giveFileName() {
//        System.out.println("Give filename:");
//        return io.readLine(">");
//    }
    
    private HashMap<String, String> createNewTagInfo() {
        return askInfo(secondTagPhase());
    }
    
    private String secondTagPhase() {
        formatInfo();
        String input = io.readLine(">");
        return chooseFormat(input);
    }
    
    private void formatInfo() {
        io.print("Choose one number from below.");
        io.print("");
        io.print("(1) Article");
        io.print("(2) Inproceedings");
        io.print("(3) Book");
        io.print("(4) manual");
    }
    
    private HashMap<String, String> askInfo(String format) {
        HashMap<String, String> result = new HashMap<String, String>();
        ArrayList<ArrayList<String>> into = lists.get(format);
        askCitationKey(format, result);
        askAndRecord(result, into.get(0), into.get(1));
        return result;
    }
    
    private void askCitationKey(String type, HashMap<String, String> result) {
        io.print("Give citation key:");
        result.put(type, io.readLine(">"));
    }
    
    private String chooseFormat(String input) {
        input.replaceAll("\\s", "");
        if (input.equals("1")) {
            return "article";
        } else if (input.equals("2")) {
            return "inproceedings";
        } else if (input.equals("3")) {
            return "book";
        } else if (input.equals("4")) {
            return "manual";
        }else {
            wrongInput();
            return secondTagPhase();
        }
    }
    
    private void askAndRecord(HashMap<String, String> result, List<String> required, List<String> optional) {
        io.print("");
        io.print("Fill required fields: ");
        
        for (String req : required) {
            askForRequiredInput(req, result);
        }
        io.print("");
        io.print("Fill optional fields: ");
        io.print("(you can skip field by pressing enter and keeping it empty)");
        
        for (String opt : optional) {
            askForOptionalInput(opt, result);
        }
    }
    
    private void askForRequiredInput(String req, HashMap<String, String> result) {
        io.print("Give required input " + req + ":");
        String input = io.readLine(">");
        input.replaceAll("\\s", "");
        if (input.equals("")) {
            askForRequiredInput(req, result);
        } else {
            result.put(req, input);
        }
    }
    
    private void askForOptionalInput(String opt, HashMap<String, String> result) {
        io.print("Give optional input " + opt + ":");
        String input = io.readLine(">");
        input.replaceAll("\\s", "");
        if (!input.equals("")) {
            result.put(opt, input);
        }
    }
    
    private void askIfContinue() {
        io.print("");
        io.print("(1) Add next tag");
        io.print("(2) Make file");
        turnSwitch(io.readLine(">"));
    }
    
    private void turnSwitch(String p) {
        if (p.equals("1")) {
            this.onSwitch = true;
        } else if (p.equals(p.equals("2"))) {
            this.onSwitch = false;
        } else {
            askIfContinue();
        }
    }
    
    public boolean getContinue() {
        return this.onSwitch;
    }
}
