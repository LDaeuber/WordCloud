package de.thm.informatik.wordcloud.luisd.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class ExtractTxt {

    private final ProcessText process;

    public ExtractTxt() {
        process = new ProcessText();
    }

    //Methode um Inhalt aus txt Datei zu extrahieren
    public void extractTXT(File file, Map<String, Integer> map, String lowercase) throws IOException {
        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String text = in.nextLine();
                process.processing(text, map, lowercase);
            }
        }
    }
}
