import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Lukasz on 2016-11-18.
 */
public class WebstersDictionary {

    private Set<String> dictionary;
    private String fileName;
    private boolean caseSensitive;

    public boolean isCaseSensitive() {
        return caseSensitive;
    }


    /**
     * Create instance basing on input file (one word per line).
     *
     * @param fileName
     */
    WebstersDictionary(String fileName, boolean isCaseSensitive) {
        this.fileName = fileName;
        this.caseSensitive = isCaseSensitive;

        retrieveDictionaryFromFile(fileName);
    }

    private void retrieveDictionaryFromFile(String fName) {
        try (Stream<String> stream = Files.lines(Paths.get(fName))) {
            dictionary = stream.map(s -> {
                if (isCaseSensitive()) {
                    return s;
                } else {
                    return s.toLowerCase();
                }
            }).sorted().collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean contains(String word) {
        return dictionary.contains(word);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    /**
     * Useful for test purpose.
     *
     * @return
     */
    public Stream<String> stream() {
        return dictionary.stream();
    }

    /**
     * Return true if only one letter is different.
     *
     * @param inputWord
     * @param nextWord
     * @return
     */
    public boolean differsByOneChar(String inputWord, String nextWord) {

        char[] inputWordCharArray;
        char[] nextWordCharArray;

        if (isCaseSensitive()) {
            inputWordCharArray = inputWord.toCharArray();
            nextWordCharArray = nextWord.toCharArray();
        } else {
            inputWordCharArray = inputWord.toLowerCase().toCharArray();
            nextWordCharArray = nextWord.toLowerCase().toCharArray();
        }


        int counter = 0;

        for (int i = 0; i < inputWord.length(); i++) {

            if (inputWordCharArray[i] != nextWordCharArray[i])
                counter++;
        }

        return counter == 1;

    }

    /**
     * Retrieve next step list basing on existing list. The last word in stepList is taken to search for next word which
     * will differ by only one letter.
     *
     * @param stepList - existing step list
     * @return set of possible next steps.
     */
    public Set<String> getNextWordsSet(LinkedList<String> stepList) {
        Set<String> nextStepSet = new HashSet<>();
        String lastWord = stepList.getLast();

        if(!isCaseSensitive())
            lastWord = lastWord.toLowerCase();

        for (String nextWord : dictionary) {

            if (nextWord.length() == lastWord.length() && !stepList.contains(nextWord) && differsByOneChar(lastWord, nextWord)) {
                nextStepSet.add(nextWord);
            }
        }
        return nextStepSet;

    }

}
