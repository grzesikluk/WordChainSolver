import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Lukasz on 2016-11-18.
 */
public class WordChainSolverTest {
    private static String dictionaryFileName = "src\\main\\resources\\testdictionary.txt";
    private WordChainSolver solver;

    private LinkedList<String> getLinedListForTest(String... str) {
        LinkedList<String> result = new LinkedList<>();

        for(int i=0;i<str.length;i++)
            result.add(str[i]);
        return result;
    }

    private Set<List<String>> getSetForTest() {
        return new HashSet<>();
    }

    @Before
    public void init() throws Exception {
        solver = new WordChainSolver(new WebstersDictionary(dictionaryFileName, false));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetPossibleSolutionChainsWrongArgumentnotEqualLength() throws Exception {
        solver.getWordChains("oneWord", "otherWord");
    }

    @Test
    public void testGetPossibleSolutionChainsSameInputAndOutputWord() throws Exception {
        Assert.assertNull(solver.getWordChains("oneWord", "oneWord"));
    }

    @Test
    public void testGetWordChainsRecursiveFunctionOneWordLoadLead() throws Exception {
        Set<List<String>> outputSet = getSetForTest();
        outputSet.add(getLinedListForTest("load","lead"));
        Assert.assertEquals(outputSet,solver.getWordChains("load","lead"));
    }

    @Test
    public void testGetWordChainsRecursiveFunctionOneWordLoadGold() throws Exception {
        Set<List<String>> outputSet = getSetForTest();
        outputSet.add(getLinedListForTest("load","toad","told","gold"));
        outputSet.add(getLinedListForTest("load","toad","goad","gold"));
        outputSet.add(getLinedListForTest("load","goad","toad","told","gold"));
        outputSet.add(getLinedListForTest("load","goad","gold"));

        Assert.assertEquals(outputSet,solver.getWordChains("load","gold"));

    }

    @Test
    public void testGetWordChainsRecursiveFunctionOneWordGoadLead() throws Exception {
        Set<List<String>> outputSet = getSetForTest();
        outputSet.add(getLinedListForTest("goad","load","lead"));
        outputSet.add(getLinedListForTest("goad","toad","load","lead"));
        outputSet.add(getLinedListForTest("goad","gold","told","toad","load","lead"));

        Assert.assertEquals(outputSet,solver.getWordChains("goad","lead"));

    }

    @Test
    public void testGetWordChainsRecursiveFunctionOneWordLoadGoat() throws Exception {
        Set<List<String>> outputSet = getSetForTest();
        Assert.assertEquals(outputSet,solver.getWordChains("load","goat"));

    }

    @Test
    public void testGetWordChainsRecursiveFunctionOneWordNonExistingInputWord() throws Exception {
        Assert.assertNull(solver.getWordChains("$$$$","goat"));

    }

    @Test
    public void testGetWordChainsRecursiveFunctionOneWordNonExistingOutput() throws Exception {
        Assert.assertNull(solver.getWordChains("load","$$$$"));

    }

}