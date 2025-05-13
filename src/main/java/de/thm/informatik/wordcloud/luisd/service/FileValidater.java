package de.thm.informatik.wordcloud.luisd.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileValidater {

    //Übergabe des Folder Paths in dem die zu extrahierenden Dateien sind
    public static List<File> loadValidFiles(String folderPath) throws IOException {
        File folder = new File(folderPath);//Initialisierung des Folders
        File[] files = folder.listFiles();//Initialisieren eines Folder Arrays in dem sich alle Dateien befinden
        List<File> validFiles = new ArrayList<>();//Deklaration ArrayList für legitime Dateien


        if(files != null){
            for(File file : files){//Durch gesamtes File Array iterieren
                if(file.isFile() && validFile(file.getName())){//wenn das akutelle objekt eine Datei ist und legitim ist dann wird es der ArrayList hinzugefügt
                    validFiles.add(file);
                }
            }
        }else{
            throw new IOException("Invalid Folder");
        }
        return validFiles;//Rückgabe der legitimen Files aus dem Folder
    }

    //Übergabe Name der aktuellen Datei
    public static boolean validFile(String filename){
        return filename.endsWith(".txt") || filename.endsWith(".pdf") ||
                filename.endsWith(".docx") || filename.endsWith(".pptx");//Prüfung ob eine der legitimen Endungen, dann true zurückgeben
    }
}
