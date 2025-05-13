package java;

import de.thm.informatik.wordcloud.luisd.domain.WordFilter;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestWordFilter {

    private WordFilter filter;

    @BeforeEach //damit immer ein neuer WordFilter erstellt wird
    void setUp(){
        filter = new WordFilter();
    }

    @Test //Test ob max words und min frequency korrekt angewandt wird
    void testMinFrequencyAndMaxWords() {
        Map<String, Integer> originalMap = new HashMap<>();

        originalMap.put("Apfel", 7);
        originalMap.put("Birne", 2);
        originalMap.put("Banane", 5);
        originalMap.put("Mango", 1);

        Map<String, Integer> filteredMap = filter.getFilteredMap(originalMap, 3, 2, "no");

        assertEquals(3, filteredMap.size());

        assertTrue(filteredMap.containsKey("Apfel"));
        assertTrue(filteredMap.containsKey("Birne"));
        assertTrue(filteredMap.containsKey("Banane"));

        assertFalse(filteredMap.containsKey("Mango"));
    }

    @Test //Test ob Keys in der Map alphabetisch sortiert sind
    void testSortAlphabetically() {
        Map<String, Integer> originalMap = new HashMap<>();

        originalMap.put("Apfel", 7);
        originalMap.put("Birne", 2);
        originalMap.put("Banane", 5);
        originalMap.put("Mango", 1);

        Map<String, Integer> filteredMap = filter.getFilteredMap(originalMap, 4, 1, "yes");

        assertEquals("Apfel", filteredMap.keySet().toArray()[0]);
        assertEquals("Banane", filteredMap.keySet().toArray()[1]);
        assertEquals("Birne", filteredMap.keySet().toArray()[2]);
        assertEquals("Mango", filteredMap.keySet().toArray()[3]);
    }

    @Test //Test ob max words korrekt angewandt wird
    void testMaxWordsLimit() {
        Map<String, Integer> originalMap = new HashMap<>();

        originalMap.put("Apfel", 7);
        originalMap.put("Birne", 5);
        originalMap.put("Kirsche", 2);

        Map<String, Integer> filteredMap = filter.getFilteredMap(originalMap, 2, 1, "no");

        assertEquals(2, filteredMap.size());

        assertTrue(filteredMap.containsKey("Apfel"));
        assertTrue(filteredMap.containsKey("Birne"));
        assertFalse(filteredMap.containsKey("Kirsche"));
    }

    @Test //Test, ob korrekt erkannt wird, dass die map ohne eingabe leer ist
    void testEmptyMap() {
        Map<String, Integer> originalMap = new HashMap<>();

        Map<String, Integer> filteredMap = filter.getFilteredMap(originalMap, 3, 1, "no");

        assertTrue(filteredMap.isEmpty());
    }

    @Test //Test ob map leer ist, wenn Wortfrequenz von allen wörtern zu niedrig ist
    void testInvalidWords() {
        Map<String, Integer> originalMap = new HashMap<>();

        originalMap.put("Apfel", 1);
        originalMap.put("Birne", 1);

        Map<String, Integer> filteredMap = filter.getFilteredMap(originalMap, 3, 2, "no");

        assertTrue(filteredMap.isEmpty());
    }
}
