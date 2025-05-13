package de.thm.informatik.wordcloud.luisd.service;

import java.util.Map;

public class ProcessText {

    //Da sich in allen 4 extract Klassen der Teil wiederholt in dem der text gesplittet wird und dann damit die Map gefüllt wird
    //gibt es hierfür eine extra Methode
    public void processing(String text, Map<String, Integer> map, String lowercase) {
        String textNew = "yes".equals(lowercase) ? text.toLowerCase() : text;
        //an allen zeichen die nicht Teil des Alphabets sind splitten
        String[] words = textNew.split("[^\\p{IsAlphabetic}]+");
        for (String word : words) {
            //wort ist kein whitespace und hat keine zahlen im wort
            if(!word.isBlank()) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
        }
    }
}
