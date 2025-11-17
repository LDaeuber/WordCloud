package de.thm.informatik.wordcloud.luisd.ui;

import de.thm.informatik.wordcloud.luisd.facade.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File; // IMPORT HINZUGEFÜGT
import java.io.IOException;
import java.util.*;

public class WordCloudUI {

    private static final Scanner in = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(WordCloudUI.class);

    public static void main(String[] args) throws IOException {

        System.out.print("Folder path: ");
        String folderPath = in.nextLine(); // z.B. "src/main/resources/samples"
        System.out.print("Language (english/german): ");
        String language = in.nextLine();
        System.out.print("Max words (1 - n): ");
        int maxWords = in.nextInt();
        in.nextLine();
        System.out.print("Min frequency (1 - n): ");
        int minFrequency = in.nextInt();
        in.nextLine();
        System.out.print("Show frequency? (yes/no): ");
        String showFrequency = in.nextLine();
        System.out.print("Group similar words? (yes/no): ");
        String group = in.nextLine();
        System.out.print("Convert lower case? (yes/no): ");
        String convertLower = in.nextLine();
        System.out.print("Sort words alphabetically? (yes/no): ");
        String sortAlphabetically = in.nextLine();
        System.out.print("Stop words: ");
        String stopWords = in.nextLine();

        startProgram(folderPath, language, maxWords, minFrequency, showFrequency, group, convertLower, sortAlphabetically, stopWords);
    }

    public static void startProgram(String folderPath, String language, int maxWords, int minFrequency,
                                     String showFrequency, String group, String convertLower,
                                     String sortAlphabetically, String stopWords) throws IOException {

        // Pfad zur Stopwords-Datei (EINGABE)
        String stopwordPath = "src" + File.separator + "main" + File.separator + "resources" + 
                              File.separator + "stopWordFolder" + File.separator + "stopwords.txt";

        // Pfad zur HTML-Vorlage (EINGABE)
        String templateBasePath = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
        String htmlInputPath = templateBasePath + "input" + File.separator + "wordcloud.html";

        // Pfad für die Ausgabedateien (AUSGABE), konsistent mit CSVWriter
        String outputBasePath = "target" + File.separator + "output" + File.separator;
        String htmlOutputPath = outputBasePath + "wordcloudOutput.html";

        // Initialisierung neuer Facade um factory Methods aufrufen zu können
        // Der 'folderPath' kommt jetzt korrekt vom Benutzer (z.B. "src/main/resources/samples")
        Facade facade = new Facade(folderPath, language, group, convertLower, stopwordPath);

        if(!stopWords.isEmpty()) {
            facade.addStopWords(stopWords);
            facade.removeStopWords();
        }

        // Erstellen neuer Map... (Ihr Code)
        Map<String, Integer> filteredMap = facade.getFilteredMap(maxWords, minFrequency, sortAlphabetically);

        // Schreiben der Dateien an die korrigierten Pfade
        facade.writeToCSVFile(filteredMap); // Nutzt bereits target/output/
        facade.writeToHTMLFile(htmlInputPath, htmlOutputPath, filteredMap, showFrequency);
        
        // Start des Programms in Browser (muss auf die target-Datei zeigen)
        Runtime.getRuntime().exec("cmd /c start " + htmlOutputPath);

        System.out.println();
        logger.info("Anzahl Wörter: {}", filteredMap.size());
        int sum = 0;
        for(int frequency : filteredMap.values()) {
            sum += frequency;
        }
        logger.info("Gesamtfrequenz: {}", sum);
    }
}