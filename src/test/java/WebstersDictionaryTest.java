import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Lukasz on 2016-11-18.
 */
public class WebstersDictionaryTest {


    private String testFile = "src\\main\\resources\\testdictionary.txt";
    private WebstersDictionary dictCaseSensitive;
    private WebstersDictionary dictNotCaseSensitive;

    @Before
    public void init() {
        dictCaseSensitive = new WebstersDictionary(testFile, true);
        dictNotCaseSensitive = new WebstersDictionary(testFile, false);
    }

    @Test
    public void testRetrievalOfElements() throws Exception {
        Assert.assertTrue( dictCaseSensitive.stream().collect(Collectors.toSet()).contains("Ababdeh"));
        Assert.assertTrue( dictCaseSensitive.stream().collect(Collectors.toSet()).contains("Ababua"));
        Assert.assertFalse( dictCaseSensitive.stream().collect(Collectors.toSet()).contains("Ababddeh"));
        Assert.assertFalse( dictCaseSensitive.stream().collect(Collectors.toSet()).contains("Abaabua"));

    }


    @Test
    public void testDiffersByOneCharNotCaseSensitive() throws Exception {
        Assert.assertTrue(dictNotCaseSensitive.differsByOneChar("abracadabra", "abracadabri"));
        Assert.assertFalse(dictNotCaseSensitive.differsByOneChar("abracadabra", "abracadabra"));
        Assert.assertFalse(dictNotCaseSensitive.differsByOneChar("abracadabra", "abracAdabra"));

    }

    @Test
    public void testDiffersByOneCharWithCaseSensitive() throws Exception {
        Assert.assertFalse(dictCaseSensitive.differsByOneChar("abracAdabra", "abracadabri"));
        Assert.assertFalse(dictCaseSensitive.differsByOneChar("abracadabra", "abracadabra"));
        Assert.assertTrue(dictCaseSensitive.differsByOneChar("abracadabra", "abracAdabra"));

    }


    @Test
    public void testGetNextWordsSet_inputSetOneWord() throws Exception {
        Set<String> outputSet = new HashSet<>();
        outputSet.add("lead");
        outputSet.add("goad");
        outputSet.add("toad");

        LinkedList<String> existingList = new LinkedList<String>();
        existingList.add("load");

        Assert.assertEquals(outputSet, dictCaseSensitive.getNextWordsSet(existingList));

    }

    @Test
    public void testGetNextWordsSet_inputTwoWords() throws Exception {
        Set<String> outputSet = new HashSet<>();
        outputSet.add("goad");
        outputSet.add("told");

        LinkedList<String> existingList = new LinkedList<String>();
        existingList.add("load");
        existingList.add("toad");

        Assert.assertEquals(outputSet, dictCaseSensitive.getNextWordsSet(existingList));

    }


}