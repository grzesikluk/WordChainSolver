import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Lukasz on 2016-11-18.
 */
public class WordChainSolver {

    private int limitChainLength = 0;
    private WebstersDictionary dictionary;
    private boolean isCaseSensitive;


    /**
     * Initialize solver. The case sensitivity is derived from dictionary.
     *
     * @param dict
     */
    WordChainSolver(WebstersDictionary dict) {
        this.dictionary = dict;
        this.isCaseSensitive = dict.isCaseSensitive();
        this.limitChainLength = 0;
    }

    /**
     * Initialize solver. The case sensitivity is derived from dictionary.
     *
     * @param dict
     */
    WordChainSolver(WebstersDictionary dict, int limitChainLen) {
        this.dictionary = dict;
        this.isCaseSensitive = dict.isCaseSensitive();
        this.limitChainLength = limitChainLen;
    }

    /**
     * Get all chains of words leading from input word to output word.
     * Restriction: input word must be the same length as output word,.
     *
     * @param inputWord
     * @param outputWord
     * @return set of all chains if any, null - if none
     */
    public Set<List<String>> getWordChains(String inputWord, String outputWord) {

        Set<List<String>> solutonSet = new HashSet<>();

        if (inputWord.length() != outputWord.length())
            throw new IllegalArgumentException();

        else {

            if (inputWord.equals(outputWord) || !dictionary.contains(inputWord) || !dictionary.contains(outputWord)) {
                return null;
            }

            /* Progress with searching */
            LinkedList<String> startList = new LinkedList<String>();
            startList.add(inputWord);

            getWordChainsRecursiveFunction(startList, outputWord, solutonSet);

        }

        return solutonSet;
    }


    /**
     * Recursive search of word chains from input to output word.
     *
     * @param inputChain - existing chain which determines what will be searched and what will be excluded from search
     * @param outputWord - last word in the chain
     * @param solutonSet - solutions found will be added to this set.
     */
    public void getWordChainsRecursiveFunction(LinkedList<String> inputChain, String outputWord, Set<List<String>> solutonSet) {

        if (limitChainLength > 0 && inputChain.size() >= limitChainLength)
            /*This condition means - limit the solution set to maximum limitChainLength*/
            return;
        else {

            Set<String> nextWordsSet = dictionary.getNextWordsSet(inputChain);

            if (nextWordsSet.isEmpty()) {
                //end condition - no chain finished by outputString
                return;
            } else {

                for (String nextWord : nextWordsSet) {

                    LinkedList<String> updatedInputChain = new LinkedList<>(inputChain);
                    updatedInputChain.add(nextWord);

                    if (compareWords(nextWord, outputWord)) {
                        System.out.println(updatedInputChain); //this is printed because this algo takes a while
                        solutonSet.add(updatedInputChain); //build solution set

                    } else {
                        getWordChainsRecursiveFunction(updatedInputChain, outputWord, solutonSet);
                    }
                }
            }
        }

    }

    private boolean compareWords(String oneWord, String otherWord) {
        if (isCaseSensitive)
            return oneWord.equals(otherWord);
        else
            return oneWord.toLowerCase().equals(otherWord.toLowerCase());
    }

}
