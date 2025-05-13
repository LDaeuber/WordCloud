package de.thm.informatik.wordcloud.luisd.service;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class ExtractPptx {

    private final ProcessText process;

    public ExtractPptx() {
        process = new ProcessText();
    }

    //Methode um Inhalt aus PowerPoint(pptx) Datei zu extrahieren
    public void extractPPTX(File file, Map<String, Integer> map, String lowercase) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XMLSlideShow ppt = new XMLSlideShow(fis)) {
            for (XSLFSlide slide : ppt.getSlides()) {
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape textShape) {
                        process.processing(textShape.getText(), map, lowercase);
                    }
                }
            }
        }
    }
}
