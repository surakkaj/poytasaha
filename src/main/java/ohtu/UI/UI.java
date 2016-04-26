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
    
    private void createMapLists() {
        this.lists = new HashMap<String, ArrayList<ArrayList<String>>>();
        String[] requiredArticle = {"author", "title", "journal", "year", "volume"};
        String[] optionalArticle = {"number", "pages", "month", "note", "key"};
        
        String[] requiredInproceedings = {"author", "title", "booktitle", "year"};
        String[] optionalInproceedings = {"editor", "volume/number", "series", "pages", "address", "month", "organization", "publisher"};
        
        String[] requiredBook = {"author/editor", "title", "publisher", "year"};
        String[] optionalBook = {"volume/editor", "series", "address", "edition", "month", "note", "key"};
        
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
        System.out.println("File didn't end in .txt or .bib");
    }
    
    private void startingInstructions() {
        //Kirjoitin souteilla että on helpompi lukuisempi
        System.out.println("Give file to write into. If file does not exist empty one will be created.");
        System.out.println("File must be in .txt or .bib format.");
        System.out.println("");
    }
    
    private void wrongInput() {
        System.out.println("Wrong input");
        System.out.println("");
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
        System.out.println("");
        System.out.println("Choose one number from below");
        System.out.println("(1) Create new reference");
        System.out.println("(2) List all existing references in file");
        System.out.println("(3) Choose existing reference to modify");
        System.out.println("(4) Choose existing reference to remove");
        System.out.println("(5) Save and quit");
    }
    
    private void lookUpExistingToModify() {
        System.out.println("");
        System.out.println("Give citation key of the reference you want to modify");
        System.out.println("");
        String input = io.readLine(">");
        Reference ref = dao.searchByCitationKey(input);
        System.out.println("");
        System.out.println(ref.toBibtex());
        System.out.println("");
        modifyReference(ref);
    }
    
    private void modifyReference(Reference ref) {
        HashMap<String, String> map = (HashMap) ref.getTags();
        ArrayList<ArrayList<String>> into = lists.get(ref.getType());
        askAndRecordToModify(map, into.get(0), into.get(1));
    }
    
    private void askAndRecordToModify(HashMap<String, String> result, List<String> required, List<String> optional) {
        System.out.println("");
        System.out.println("Fill required fields: ");
        
        for (String req : required) {
            String current = result.get(req);
            
            System.out.println("current " + req + " is " + current);
            askForRequiredInput(req, result);
        }
        System.out.println("");
        System.out.println("Fill optional fields: ");
        System.out.println("(you can skip field by pressing enter and keeping it empty)");
        
        for (String opt : optional) {
            String current = "";
            if (result.containsKey(opt)) {
                current = result.get(opt);
            }
            System.out.println("current " + opt + " is " + current);
            askForOptionalInput(opt, result);
        }
    }
    
    private void listContentOfFile() {
        System.out.println("");
        System.out.println("Listing content of file");
        System.out.println(dao.toBibtex());
    }
    
    private void removeExistingReference() {
        System.out.println("");
        System.out.println("give citationkey to remove ");
        System.out.println("");
        String input = io.readLine(">");
        Reference ref = dao.searchByCitationKey(input);
        if (ref != null) {
            dao.listAll().remove(ref);
        }
    }
    
    private String giveFileName() {
        System.out.println("Give filename:");
        return io.readLine(">");
    }
    
    private HashMap<String, String> createNewTagInfo() {
        return askInfo(secondTagPhase());
    }
    
    private String secondTagPhase() {
        formatInfo();
        String input = io.readLine(">");
        return chooseFormat(input);
    }
    
    private void formatInfo() {
        System.out.println("Choose one number from below.");
        System.out.println("");
        System.out.println("(1) Article");
        System.out.println("(2) Inproceedings");
        System.out.println("(3) Book");
    }
    
    private HashMap<String, String> askInfo(String format) {
        HashMap<String, String> result = new HashMap<String, String>();
        ArrayList<ArrayList<String>> into = lists.get(format);
        askCitationKey(format, result);
        askAndRecord(result, into.get(0), into.get(1));
        return result;
    }
    
    private void askCitationKey(String type, HashMap<String, String> result) {
        System.out.println("Give citation key:");
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
        } else {
            wrongInput();
            return secondTagPhase();
        }
    }
    
    private void askAndRecord(HashMap<String, String> result, List<String> required, List<String> optional) {
        System.out.println("");
        System.out.println("Fill required fields: ");
        
        for (String req : required) {
            askForRequiredInput(req, result);
        }
        System.out.println("");
        System.out.println("Fill optional fields: ");
        System.out.println("(you can skip field by pressing enter and keeping it empty)");
        
        for (String opt : optional) {
            askForOptionalInput(opt, result);
        }
    }
    
    private void askForRequiredInput(String req, HashMap<String, String> result) {
        System.out.println("Give required input " + req + ":");
        String input = io.readLine(">");
        input.replaceAll("\\s", "");
        if (input.equals("")) {
            askForRequiredInput(req, result);
        } else {
            result.put(req, input);
        }
    }
    
    private void askForOptionalInput(String opt, HashMap<String, String> result) {
        System.out.println("Give optional input " + opt + ":");
        String input = io.readLine(">");
        input.replaceAll("\\s", "");
        if (!input.equals("")) {
            result.put(opt, input);
        }
    }
    
    private void askIfContinue() {
        System.out.println("");
        System.out.println("(1) Add next tag");
        System.out.println("(2) Make file");
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
