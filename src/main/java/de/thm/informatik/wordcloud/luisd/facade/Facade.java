package de.thm.informatik.wordcloud.luisd.facade;

import de.thm.informatik.wordcloud.luisd.domain.TextHandler;
import de.thm.informatik.wordcloud.luisd.domain.WordFilter;
import de.thm.informatik.wordcloud.luisd.export.CSVWriter;
import de.thm.informatik.wordcloud.luisd.export.HTMLWriter;
import de.thm.informatik.wordcloud.luisd.service.StopWordsHandling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Facade {
    private final TextHandler handler;
    private final StopWordsHandling stopWordsHandling;
    private final String stopwordFile;
    private final WordFilter filter;

    //Facade Konstruktor mit initialisierung eines TextExtractors mit allen nötigen Werten
    public Facade(String folderPath, String language, String group, String convertLower, String stopwordFile) throws IOException {
        this.handler = new TextHandler(folderPath, language, group, convertLower, stopwordFile);
        this.stopWordsHandling = new StopWordsHandling();
        this.stopwordFile = stopwordFile;
        this.filter = new WordFilter();
    }

    //Factory-Methoden um Domain-Klassen zu instanziieren.
    //Dadurch kommuniziert die Main-Klasse ausschließlich mit der Fassade
    public void addStopWords(String input) throws FileNotFoundException {
        stopWordsHandling.addStopWords(input, stopwordFile);
    }

    public void writeToHTMLFile(String filenameInput, String filenameOutput, Map<String, Integer> map, String frequency) throws IOException {
        HTMLWriter.writeToHTMLFile(filenameInput, filenameOutput, map, frequency);
    }

    public void writeToCSVFile(Map<String, Integer> map) throws IOException {
        CSVWriter.writeToCSVFile(map);
    }

    public Map<String, Integer> getMap() {
        return handler.getMap();
    }

    public void removeStopWords() throws IOException {
        stopWordsHandling.removeWords(handler.getStopwordFile(), handler.getMap());
    }

    public Map<String, Integer> getFilteredMap(int maxWords, int minFrequency, String sortAlphabetically) {
        return filter.getFilteredMap(handler.getMap(), maxWords, minFrequency, sortAlphabetically);
    }

}
