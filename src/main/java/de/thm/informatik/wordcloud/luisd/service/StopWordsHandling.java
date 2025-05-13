package de.thm.informatik.wordcloud.luisd.service;

import java.io.*;
import java.util.*;

public class StopWordsHandling {

    //Methode um vorgegebene Stopwords aus der Map zu entfernen
    public void removeWords(String stopwordFile, Map<String, Integer> map) throws IOException {
        File stopFile = new File(stopwordFile);
        if (!stopFile.exists()) {
            return;
        }
        //Bessere Laufzeit Komplexität als ArrayList, im Schnitt O(1), jeder begriff wird nur einmal gespeichert
        Set<String> stopWords = new HashSet<>();
        try (Scanner in = new Scanner(stopFile)) {
            while (in.hasNextLine()) {
                for(String word : in.nextLine().split("\\s+")) {
                    stopWords.add(word.trim());
                }
            }
        }

        //Alle stopwords aus Map entfernen
        for(String stopword : stopWords){
            map.remove(stopword);
        }
    }

    //Methode zum manuellen hinzufügen neuer Stopwords
    public void addStopWords(String stopWordsInput, String stopwordFile) throws FileNotFoundException {
        List<String> stopWords = new ArrayList<>();

        try (Scanner in = new Scanner(new File(stopwordFile))) {
            while (in.hasNextLine()) {
                for(String word : in.nextLine().split("\\W+")){
                    stopWords.add(word.toLowerCase().trim());
                }
            }
        }
        //Initialisierung BufferedWriter mit Pfad zu stopwords Datei, true damit vorhandenes nicht überschrieben wird
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(stopwordFile, true))) {
            //Iteration durch alle Wörter der neuen Stopwords und hinzufügen dieser in stopword Datei
            for (String word : stopWordsInput.split("\\s+")) {
                if (!word.isBlank() && !stopWords.contains(word)) {
                    writer.write(word.trim());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
    }
}
