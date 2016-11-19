import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Lukasz on 2016-11-18.
 */
public class WordChainSolver {

    private WebstersDictionary dictionary;
    private static final String fileName = "src\\main\\resources\\websters-dictionary.txt";
    private Set<List<String>> solutonSet;
    private boolean isCaseSensitive;

    public static void main(String[] args) {

        if (args.length >= 2) {

            String inputWord = args[0];
            String outputWord = args[1];
            String fName = fileName;

            if (args.length == 3)
                fName = args[2];

            WordChainSolver solver = new WordChainSolver(new WebstersDictionary(fName,false));
            System.out.println(solver.getWordChains(inputWord, outputWord));


        } else {
            System.err.println("Invalid parameters list \n\tUsage:\n\tWordChainSolver inputWord outputWord [dictionaryFile]");
        }

    }


    WordChainSolver(WebstersDictionary dict) {
        dictionary = dict;
        solutonSet = new HashSet<>();
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
    }


    public Set<List<String>> getWordChains(String inputWord, String outputWord) {

        if (inputWord.length() != outputWord.length())
            throw new IllegalArgumentException();

        else {

            if (inputWord.equals(outputWord) || ! dictionary.contains(inputWord) || !dictionary.contains(outputWord)) {
                return null;
            }

            LinkedList<String> startList = new LinkedList<String>();
            startList.add(inputWord);

            solutonSet.clear();
            getWordChainsRecursiveFunction(startList, outputWord);

        }

        return solutonSet;
    }

    public void getWordChainsRecursiveFunction(LinkedList<String> inputChain, String outputWord) {

        Set<String> nextWordsSet = dictionary.getNextWordsSet(inputChain);

        if (nextWordsSet.isEmpty()) {
            //end condition - no chain finished by outputString
            return;
        } else {

            for (String nextWord : nextWordsSet) {

                LinkedList<String> updatedInputChain = new LinkedList<>(inputChain);
                updatedInputChain.add(nextWord);

                if (compareWords(nextWord,outputWord)) {
                    System.out.println(updatedInputChain);
                    solutonSet.add(updatedInputChain); //build solution set

                } else {
                    getWordChainsRecursiveFunction(updatedInputChain, outputWord);
                }
            }
        }

    }

    private boolean compareWords(String oneWord, String otherWord) {
        if(isCaseSensitive())
            return oneWord.equals(otherWord);
        else
            return oneWord.toLowerCase().equals(otherWord.toLowerCase());
    }

}
