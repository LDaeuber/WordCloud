package de.thm.informatik.wordcloud.luisd.domain;

import de.thm.informatik.wordcloud.luisd.service.*;

import java.io.*;
import java.util.*;

public class TextHandler {
    private final Map<String, Integer> map = new HashMap<>();
    private final String language;
    private final String groupWords;
    private final String convertToLower;
    private final String stopwordFile;

    private final ExtractTxt exTXT;
    private final ExtractPdf exPDF;
    private final ExtractDocx exDOCX;
    private final ExtractPptx exPPTX;

    private StopWordsHandling stopWordsHandling;


    public TextHandler(String folderPath, String language, String group, String convertLower, String stopwordFile) throws IOException {
        this.language = language;
        this.groupWords = group;
        this.convertToLower = convertLower;
        this.stopwordFile = stopwordFile;

        this.exTXT = new ExtractTxt();
        this.exPDF = new ExtractPdf();
        this.exDOCX = new ExtractDocx();
        this.exPPTX = new ExtractPptx();

        this.stopWordsHandling = new StopWordsHandling();
        processFiles(FileValidater.loadValidFiles(folderPath));//aufruf processFiles mit methode loadValidFiles aus FileValidater mit path,
                                                            // heißt es wird die ArrayList mit legitimen Dateien übergeben
    }

    private void processFiles(List<File> files) throws IOException {
        //Iteration durch legitime Dateien
        for (File file : files) {
            String name = file.getName().toLowerCase();
            //cases für verschiedene Endungen da verschieden extrahiert werden muss
            if (name.endsWith(".txt")) {
                exTXT.extractTXT(file, map, convertToLower);
            } else if (name.endsWith(".pdf")) {
                exPDF.extractPDF(file, map, convertToLower);
            } else if (name.endsWith(".docx")) {
                exDOCX.extractDOCX(file, map, convertToLower);
            } else if (name.endsWith(".pptx")) {
                exPPTX.extractPPTX(file, map, convertToLower);
            }
        }
        //grundsätzlich werden die stopwords aus der map entfernt
        stopWordsHandling.removeWords(stopwordFile, map);
        //wenn groupWords yes(true) dann wird die Methode tokenizeAndStemming aufgerufen
        if ("yes".equals(groupWords)) {
            TextTokenizer tokenizer = new TextTokenizer(language);
            Map<String, Integer> stemmed = tokenizer.tokenizeAndStem(map);
            map.clear();
            map.putAll(stemmed);
        }
    }

    //Getter für HashMap
    public Map<String, Integer> getMap() {
        return map;
    }

    //Getter für Stopword File
    public String getStopwordFile(){
        return stopwordFile;
    }
}
