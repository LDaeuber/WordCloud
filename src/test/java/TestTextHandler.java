import de.thm.informatik.wordcloud.luisd.domain.TextHandler;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestTextHandler {

    private final String testFolder = "C:\\Users\\luisd\\IdeaProjects\\WordCloud-Testat3-PR2\\src\\test\\resources\\testFolder";
    private final String language = "english";
    private final String groupWords = "yes";
    private final String convertToLower = "yes";
    private final String stopWordFile = "C:\\Users\\luisd\\IdeaProjects\\WordCloud-Testat3-PR2\\src\\test\\resources\\stopWordFolder\\stopwords.txt";

    private TextHandler handler;

    @Test
    void testProcessFiles() throws IOException {
        handler = new TextHandler(testFolder, language, groupWords, convertToLower, stopWordFile);
        Map<String, Integer> map = handler.getMap();

        assertFalse(map.isEmpty(), "Es sollten Wörter enthalten sein");
    }

    @Test
    void testStopWordRemoval() throws IOException {
        handler = new TextHandler(testFolder, language, groupWords, convertToLower, stopWordFile);
        Map<String, Integer> map = handler.getMap();

        assertFalse(map.containsKey("Apfel"), "Das Wort Apfel darf nicht enthalten sein");
    }

    @Test
    void testTokenizeAndStemming() throws IOException {
        handler = new TextHandler(testFolder, language, groupWords, convertToLower, stopWordFile);
        Map<String, Integer> map = handler.getMap();

        assertFalse(map.containsKey("running"), "Das Wort running darf nicht enthalten sein");

        assertTrue(map.containsKey("run"), "Das Wort run muss enthalten sein");
    }

    @Test
    void testEmptyFile() {
        String emptyFile = "C:\\Users\\luisd\\IdeaProjects\\WordCloud-Testat3-PR2\\src\\test\\resources\\emptyFileFolder\\test.txt";

        //Stelle sicher, dass eine IOException geworfen wird, wenn der Ordner ungültig ist.
        assertThrows(IOException.class, () -> {
            handler = new TextHandler(emptyFile, language, groupWords, convertToLower, stopWordFile);
            handler.getMap();  //Hier sollte die IOException geworfen werden
        }, "Es sollte eine IOException mit der Nachricht 'Invalid Folder' geworfen werden");
    }

    @Test
    void testInvalidFileProcessing() throws IOException {
        String invalidFiles = "C:\\Users\\luisd\\IdeaProjects\\WordCloud-Testat3-PR2\\src\\test\\resources\\invalidFileFolder";
        handler = new TextHandler(invalidFiles, language, groupWords, convertToLower, stopWordFile);
        Map<String, Integer> map = handler.getMap();

        assertTrue(map.isEmpty(), "Map sollte leer sein");
    }

    @Test
    void testConvertToLower() throws IOException {
        handler = new TextHandler(testFolder, language, groupWords, convertToLower, stopWordFile);
        Map<String, Integer> map = handler.getMap();

        for(String wort : map.keySet()){
            assertEquals(wort, wort.toLowerCase(), "Das Wort sollte klein geschrieben sein");
        }
    }
}
