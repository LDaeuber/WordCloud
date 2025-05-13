package de.thm.informatik.wordcloud.luisd.ui;

import de.thm.informatik.wordcloud.luisd.facade.Facade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class WordCloudUI {

    private static final Scanner in = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(WordCloudUI.class);

    public static void main(String[] args) throws IOException {

        System.out.print("Folder path: ");
        String folderPath = in.nextLine();
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

        //dynamischer Pfad zu den zu extrahierenden Dateien und der Stopwords Datei
        String stopwordPath = "C:\\Users\\luisd\\IdeaProjects\\WordCloud-Testat3-PR2\\src\\main\\resources\\stopWordFolder\\stopwords.txt";

        //Initialisierung neuer Facade um factory Methods aufrufen zu können
        Facade facade = new Facade(folderPath, language, group, convertLower, stopwordPath);

        if(!stopWords.isEmpty()) {
            facade.addStopWords(stopWords);
            facade.removeStopWords();
        }

        //Erstellen neuer Map um die Vorgabe der MaxWords einzuhalten, die Datei wird mit der alten gefüllt bis sie
        //die Vorgabe der maxWords erreicht hat, außerdem wird ggf. nach Aplhabet sortier
        Map<String, Integer> filteredMap = facade.getFilteredMap(maxWords, minFrequency, sortAlphabetically);

        //dynamischer Pfad zu der einzulesenden und auszugebenden html Datei
        String basePath = "C:\\Users\\luisd\\IdeaProjects\\WordCloud-Testat3-PR2\\src\\main\\resources\\";
        facade.writeToCSVFile(filteredMap);
        facade.writeToHTMLFile(basePath + "input\\wordcloud.html", basePath + "output\\wordcloudOutput.html", filteredMap, showFrequency);
        //Start des Programms in Browser
        Runtime.getRuntime().exec("cmd /c start " + basePath + "output\\wordcloudOutput.html");

        System.out.println();
        logger.info("Anzahl Wörter: {}", filteredMap.size());
        int sum = 0;
        for(int frequency : filteredMap.values()) {
            sum += frequency;
        }
        logger.info("Gesamtfrequenz: {}", sum);
    }
}
