/**
 * Created by Lukasz on 2016-11-19.
 */
public class Solution {
    private static final String dictionaryFileName = "src\\main\\resources\\websters-dictionary.txt";


    public static void main(String[] args) {

        boolean caseSensitiveSearch = false;
        int limitOnChainOfWords = 5;

        if (args.length >= 3) {

            /*Get some arguments */
            String inputWord = args[0];  //input word
            String outputWord = args[1]; //output word
            limitOnChainOfWords = new Integer(args[2]);

            String dictFileName = dictionaryFileName;

            if (args.length == 4)
                dictFileName = args[3];  //dictionary filename is optional

            WordChainSolver solver = new WordChainSolver(new WebstersDictionary(dictFileName, caseSensitiveSearch).getSubsetDictionary(inputWord.length()),limitOnChainOfWords);

            /*Start searching and print result*/
            System.out.println(solver.getWordChains(inputWord, outputWord));

        } else {
            System.err.println("Invalid parameters list \n\tUsage:\n\tSolution inputWord outputWord limitOfChain [dictionaryFile]");
        }

    }
}
