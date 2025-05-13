package de.thm.informatik.wordcloud.luisd.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class ExtractDocx {

    private final ProcessText process;

    public ExtractDocx() {
        process = new ProcessText();
    }

    //Methode um Inhalt aus Word(docx) Datei zu extrahieren
    public void extractDOCX(File file, Map<String, Integer> map, String lowercase) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument doc = new XWPFDocument(fis)) {
            for (XWPFParagraph para : doc.getParagraphs()) {
                process.processing(para.getText(), map, lowercase);
            }
        }
    }
}
