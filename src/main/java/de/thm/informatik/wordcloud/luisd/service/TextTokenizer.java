package de.thm.informatik.wordcloud.luisd.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class TextTokenizer {

    private final Analyzer analyzer;

    public TextTokenizer(String language) {
        //Auswahl ob der Analyzer English oder Deutsch sein soll, default ist English
        if ("german".equals(language.toLowerCase())) {
            this.analyzer = new GermanAnalyzer();
        } else {
            this.analyzer = new EnglishAnalyzer();
        }
    }

    //Methode um Wortstamm von Wörtern zu finden und diese zu generalisieren
    public Map<String, Integer> tokenizeAndStem(Map<String, Integer> inputMap) throws IOException {
        Map<String, Integer> stemmedMap = new HashMap<>();

        //Iteration über key und value der nicht gestemmten Map
        for (Map.Entry<String, Integer> entry : inputMap.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();

            //Öffnet Token Stream, analyzer öffnet token stream für zu analysierendes Wort(word(key)), content(irrelevant)
            try (TokenStream stream = analyzer.tokenStream("content", new StringReader(word))) {
                //bereitet stream vor
                stream.reset();
                //fügt dem stream ein Attribut hinzu mit dem man auf das Token zugreifen kann, repräsentiert Zeichenfolge
                CharTermAttribute attr = stream.addAttribute(CharTermAttribute.class);
                // wenn nächstes Token vorhanden dann true
                if (stream.incrementToken()) {
                    //erstellt gestemmtes Wort bsp. run <-- running
                    String stemmed = attr.toString();
                    //stemmed != leer
                    if (!stemmed.isEmpty()) {
                        //fügt gestemmtes Wort in Map hinzu, fügt Frequenz(value) hinzu sonst 0, erhöht value um original value
                        stemmedMap.put(stemmed, stemmedMap.getOrDefault(stemmed, 0) + count);
                    }
                }
                stream.end();
            } catch (IOException e) {
                throw new IOException("Error while processing token", e);
            }
        }

        return stemmedMap;
    }
}
