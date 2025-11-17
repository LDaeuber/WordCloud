package de.thm.informatik.wordcloud.luisd.export;

import java.io.*;
import java.util.Map;

public class HTMLWriter {

    //Methode um Inhalt der Map in html Datei zu schreiben
    public static void writeToHTMLFile(String filenameInput, String filenameOutput, Map<String, Integer> map, String frequency) throws IOException {
        StringBuilder builder = new StringBuilder();
        
        // einlesen der vorgegebenen html Datei (filenameInput ist jetzt relativ, z.B. src/main/resources/input/wordcloud.html)
        // HINWEIS: Verwenden Sie UTF-8, um Kodierungsprobleme zu vermeiden
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameInput), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            // Verbesserte Fehlermeldung
            throw new IOException("Error reading HTML template file: " + filenameInput, e);
        }

        String templateContent = builder.toString();
        // entfernen aller vorhanden span Objekte(alte Wordcloud)
        templateContent = templateContent.replaceAll("<span[^>]*>.*?</span>", "");

        StringBuilder tagCloudHtml = new StringBuilder();

        // Iteration durch gesamte Map (Ihr Code)...
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String className;
            if ("yes".equals(frequency)) {
                className = getWeight(entry.getValue());
                tagCloudHtml.append("<span class=\"wrd ").append(className).append("\">")
                        .append("<a href=\"https://www.google.com/search?q=")
                        .append(entry.getKey())
                        .append("\" target=\"_blank\">")
                        .append(entry.getKey() + "(" + entry.getValue() + ")")
                        .append("</a></span>\n");

            } else {
                className = "tagcloud2";  // Alle haben die gleiche Schriftgröße
                tagCloudHtml.append("<span class=\"wrd ").append(className).append("\">")
                        .append("<a href=\"https://www.google.com/search?q=")
                        .append(entry.getKey())
                        .append("\" target=\"_blank\">")
                        .append(entry.getKey())
                        .append("</a></span>\n");
            }
        }

        // Initialisierung der Variable... (Ihr Code)
        String newContent = templateContent.replace("<!-- TODO: Hier die generierten Tags einsetzen -->", tagCloudHtml.toString());

        File outputFile = new File(filenameOutput); // filenameOutput ist jetzt target/output/wordcloudOutput.html
        File outputDir = outputFile.getParentFile();
        
        if (outputDir != null && !outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Could not create output directory: " + outputDir.getAbsolutePath());
            }
        }

        // Schreiben der neuen span Objekte in modifizierte html Datei(output Datei)
        // HINWEIS: Verwenden Sie UTF-8, um Kodierungsprobleme zu vermeiden
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"))) {
            writer.write(newContent);
        } catch (IOException e) {
            // Verbesserte Fehlermeldung
            throw new IOException("Error writing HTML output file: " + filenameOutput, e);
        }
        
        System.out.println("[INFO] HTML file written successfully to: " + filenameOutput);
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