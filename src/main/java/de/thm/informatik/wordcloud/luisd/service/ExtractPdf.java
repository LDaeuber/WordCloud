package de.thm.informatik.wordcloud.luisd.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ExtractPdf {

    private final ProcessText process;

    public ExtractPdf() {
        process = new ProcessText();
    }

    //Methode um Inhalt aus pdf Datei zu extrahieren
    public void extractPDF(File file, Map<String, Integer> map, String lowercase) throws IOException {
        try (PDDocument doc = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);
            process.processing(text, map, lowercase);
        }
    }
}
