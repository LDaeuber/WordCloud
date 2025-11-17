package de.thm.informatik.wordcloud.luisd.export;

import java.io.BufferedWriter;
import java.io.File; // Import hinzugefügt
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CSVWriter {

    public static void writeToCSVFile(Map<String, Integer> map) throws IOException {
                
        String basePath = "target" + File.separator + "output" + File.separator;

        File outputDir = new File(basePath);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Could not create output directory: " + basePath);
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String timeStamp = formatter.format(new Date());

        String filenameOutput = basePath + timeStamp + "_wordcloud.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filenameOutput))) {
            writer.write("Word,Frequency");
            writer.newLine();

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String line = String.format("%s,%d", entry.getKey(), entry.getValue());
                writer.write(line);
                writer.newLine();
            }
            
            System.out.println("[INFO] CSV file written successfully to: " + filenameOutput);

        } catch (IOException e) {
            throw new IOException("Error writing to CSV file at path: " + filenameOutput, e);
        }
    }
}