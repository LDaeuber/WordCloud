package de.thm.informatik.wordcloud.luisd.export;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CSVWriter {

    public static void writeToCSVFile(Map<String, Integer> map) throws IOException {
        //Basis-Pfad für output
        String basePath = "C:\\Users\\luisd\\IdeaProjects\\WordCloud-Testat3-PR2\\src\\main\\resources\\output\\";
        //Zeitstempel für Dateinamen
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String timeStamp = formatter.format(new Date());

        String filenameOutput = basePath + timeStamp + "_wordcloud.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filenameOutput))) {
            //Header schreiben
            writer.write("Word,Frequency");
            writer.newLine();

            //Zeilen aus der Map schreiben
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String line = String.format("%s,%d", entry.getKey(), entry.getValue());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error writing to CSV file!", e);
        }
    }
}
