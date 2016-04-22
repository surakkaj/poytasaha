/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ohtu.data_access.FileReferenceDao;

/**
 *
 * @author melchan
 */
public class UI {

    private Scanner reader;
    private FileReferenceDao dao;
    private boolean onSwitch;

    public UI(Scanner reader, FileReferenceDao dao) {
        this.reader = reader;
        this.dao = dao;
        this.onSwitch = true;
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
        String input = reader.nextLine();
        while (fileIsRightFormat(input)) {
            wrongFileType();
            input = reader.nextLine();
        }
        while (this.onSwitch) {
            askWhatUserWantsToDo();
        }
        dao.save(input);
    }

    private boolean fileIsRightFormat(String input) {
        return input.endsWith(".txt") || input.endsWith(".bib");
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
        String input = reader.nextLine();
        input.replaceAll("\\s", "");
        if (input.equals("1")) {
            dao.add(createNewTagInfo());
        } else if (input.equals("2")) {
            //TODO List of existing rreferences in file
        } else if (input.equals("3")) {
            //TODO Choose existing reference to modify
        } else if (input.equals("4")) {
            //TODO Choose existing reference to remove
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

    private String giveFileName() {
        System.out.println("Give filename:");
        return reader.nextLine();
    }

    private HashMap<String, String> createNewTagInfo() {
        return askInfo(secondTagPhase());
    }

    private String secondTagPhase() {
        formatInfo();
        String input = reader.nextLine();
        return chooseFormat(input);
    }

    private void formatInfo() {
        System.out.println("Choose one number from below.");
        System.out.println("");
        System.out.println("(1) Article");
        System.out.println("(2) Inproceedings");
        System.out.println("(3) book");
    }

    private HashMap<String, String> askInfo(String format) {
        HashMap<String, String> result = new HashMap<String, String>();
        if (format.equals("article")) {
            askCitationKey(format, result);
            askArticle(result);
        } else if (format.equals("inproceedings")) {
            askCitationKey(format, result);
            askInbroceedings(result);
        } else if (format.equals("book")) {
            askCitationKey(format, result);
            askBook(result);
        }
        return result;
    }

    private void askCitationKey(String type, HashMap<String, String> result) {
        System.out.println("Give citation key:");
        result.put(type, reader.nextLine());
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

    private ArrayList<String> addToArrayList(String[] array) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    private void askArticle(HashMap<String, String> result) {
        String[] requiredList = {"author", "title", "journal", "year", "volume"};
        ArrayList<String> required = addToArrayList(requiredList);

        String[] optionalList = {"number", "pages", "month", "note", "key"};
        ArrayList<String> optional = addToArrayList(optionalList);

        askAndRecord(result, required, optional);
    }

    private void askInbroceedings(HashMap<String, String> result) {
        String[] requiredList = {"author", "title", "booktitle", "year"};
        ArrayList<String> required = addToArrayList(requiredList);

        String[] optionalList = {"editor", "volume/number", "series", "pages", "address", "month", "organization", "publisher"};
        ArrayList<String> optional = addToArrayList(optionalList);

        askAndRecord(result, required, optional);
    }

    private void askBook(HashMap<String, String> result) {
        String[] requiredList = {"author/editor", "title", "publisher", "year"};
        ArrayList<String> required = addToArrayList(requiredList);

        String[] optionalList = {"volume/editor", "series", "address", "edition", "month", "note", "key"};
        ArrayList<String> optional = addToArrayList(optionalList);

        askAndRecord(result, required, optional);
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
        String input = reader.nextLine();
        input.replaceAll("\\s", "");
        if (input.equals("")) {
            askForRequiredInput(req, result);
        } else {
            result.put(req, input);
        }
    }

    private void askForOptionalInput(String opt, HashMap<String, String> result) {
        System.out.println("Give required input " + opt + ":");
        String input = reader.nextLine();
        input.replaceAll("\\s", "");
        if (!input.equals("")) {
            result.put(opt, input);
        }
    }

    private void askIfContinue() {
        System.out.println("");
        System.out.println("(1) Add next tag");
        System.out.println("(2) Make file");
        turnSwitch(reader.nextLine());
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