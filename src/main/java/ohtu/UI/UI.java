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

    public UI(Scanner reader, FileReferenceDao dao) {
        this.reader = reader;
        this.dao = dao;
    }

    /**
     * Execute text ui.
     */
    public void run() {
        firstPhase();
    }

    private void firstPhase() {
        startingInstructions();
        String input = reader.nextLine();
        chooseFile(input);
    }

    private void startingInstructions() {
        //Kirjoitin souteilla että on helpompi lukuisempi
        System.out.println("Choose one number from below.");
        System.out.println("");
        System.out.println("(1) New file");
        System.out.println("(2) Choose file");

    }

    private void wrongInput() {
        System.out.println("Wrong input");
        System.out.println("");
    }

    private void chooseFile(String input) {
        HashMap<String, String> info = null;
        input.replaceAll("\\s", "");
        if (input.equals("1")) {
            info = createNewTagInfo();
        } else if (input.equals("2")) {
            info = createNewTagInfo(giveFileName());
        } else {
            wrongInput();
            firstPhase();
        }
        dao.add(info);
        //TODO metodi joka ottaa syotteeksi hashmapin ja luo tagi olion.
    }

    private String giveFileName() {
        System.out.println("Give filename:");
        return reader.nextLine();
    }

    private HashMap<String, String> createNewTagInfo() {
        return askInfo(secondPhase());
    }

    private HashMap<String, String> createNewTagInfo(String fileName) {
        HashMap<String, String> result = createNewTagInfo();
        result.put("filename", fileName);
        return result;
    }

    private String secondPhase() {
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
            return secondPhase();
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
}
