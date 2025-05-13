package de.thm.informatik.wordcloud.luisd.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WordFilter {

    //Methode um endgültig verarbeitet Map zu erstellen
    public Map<String, Integer> getFilteredMap(Map<String, Integer> originalMap, int maxWords, int minFrequency, String sortAlphabetically) {
        //Linked HashMap Initialisieren um alphabetische Sortierung aufrecht zu erhalten
        Map<String, Integer> filteredMap = new LinkedHashMap<>();

        //Verschachtelte ArrayList mit HashMap entry um zuverlässiger sortieren zu können und sonst geht comparator nicht
        List<Map.Entry<String, Integer>> entries = new ArrayList<>();

        //Über original Map iterieren und in neue map hinzufügen
        for (Map.Entry<String, Integer> entry : originalMap.entrySet()) {
            if (entry.getValue() >= minFrequency) {
                entries.add(entry);
            }
        }

        //wenn sortAlphabetically dann mit comparator alphabetisch sortieren
        if ("yes".equals(sortAlphabetically)) {
            entries.sort((w1, w2) -> w1.getKey().compareToIgnoreCase(w2.getKey()));
        }

        //neue Map befüllen bis maxWords erreicht ist
        int count = 0;
        for (Map.Entry<String, Integer> entry : entries) {
            if (count >= maxWords) {
                break;
            }
            filteredMap.put(entry.getKey(), entry.getValue());
            count++;
        }

        return filteredMap;
    }
}
