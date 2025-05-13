package de.thm.informatik.wordcloud.luisd.export;

import java.io.*;
import java.util.Map;

public class HTMLWriter {

    //Methode um Inhalt der Map in html Datei zu schreiben
    public static void writeToHTMLFile(String filenameInput, String filenameOutput, Map<String, Integer> map, String frequency) throws IOException {
        StringBuilder builder = new StringBuilder();
        //einlesen der vorgegebenen html Datei
        try (BufferedReader reader = new BufferedReader(new FileReader(filenameInput))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new IOException("Error reading file");
        }

        String templateContent = builder.toString();
        //entfernen aller vorhanden span Objekte(alte Wordcloud)
        templateContent = templateContent.replaceAll("<span[^>]*>.*?</span>", "");

        StringBuilder tagCloudHtml = new StringBuilder();

        //Teration durch gesamte Map
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            //Methode um aufgrund des map Values(Frequenz) eine font thickness zu bestimmen
            String className;
            if ("yes".equals(frequency)) {
                // Wenn frequency true ist, benutze getClassForWeight, um die Klasse basierend auf der Häufigkeit zu setzen
                className = getWeight(entry.getValue());

                tagCloudHtml.append("<span class=\"wrd ").append(className).append("\">")
                        .append("<a href=\"https://www.google.com/search?q=")
                        .append(entry.getKey())
                        .append("\" target=\"_blank\">")
                        .append(entry.getKey() + "(" + entry.getValue() + ")")
                        .append("</a></span>\n");

            } else {
                // Wenn frequency false ist, verwenden wir eine Standard-Klasse (z.B. tagcloud4)

                className = "tagcloud2";  // Alle haben die gleiche Schriftgröße

                tagCloudHtml.append("<span class=\"wrd ").append(className).append("\">")
                        .append("<a href=\"https://www.google.com/search?q=")
                        .append(entry.getKey())
                        .append("\" target=\"_blank\">")
                        .append(entry.getKey())
                        .append("</a></span>\n");
            }
        }

        //Initialisierung der Variable die alle neuen span Objekte enthält
        //diese neuen span Objekte ersetzen den angegeben Marker, also werden hinzugefügt
        String newContent = templateContent.replace("<!-- TODO: Hier die generierten Tags einsetzen -->", tagCloudHtml.toString());

        //Schreiben der neuen span Objekte in modifizierte html Datei(output Datei)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filenameOutput))) {
            writer.write(newContent);
        } catch (IOException e) {
            throw new IOException("Error writing file");
        }
    }

    //Methode um basierend auf der Frequenz(map Value) eine Schriftstärke(font thickness) festzulegen
    private static String getWeight(int weight) {
        if(weight >= 20){
            return "tagcloud10";
        } else if (weight >= 18) {
            return "tagcloud9";
        } else if (weight >= 16) {
            return "tagcloud8";
        } else if (weight >= 14) {
            return "tagcloud7";
        } else if (weight >= 12) {
            return "tagcloud6";
        } else if (weight >= 10) {
            return "tagcloud5";
        } else if (weight >= 8) {
            return "tagcloud4";
        } else if (weight >= 6) {
            return "tagcloud3";
        } else if (weight >= 4) {
            return "tagcloud2";
        }else if (weight >= 2) {
            return "tagcloud1";
        }else {
            return "tagcloud0";
        }
    }
}
