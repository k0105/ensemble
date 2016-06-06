import de.rwthaachen.ensemble.postprocessing.AnswerMerger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * Created by fp_work on 22.12.15.
 */
public class AnswerMergingTest {
    AnswerMerger answerMerger;

    @BeforeSuite
    public void beforeTest() {
        answerMerger = new AnswerMerger();
    }

    @Test()
    public void testAuthorIdExtraction() {
        Set<char[]> result = answerMerger.mergeAnswers("ajlsklthisisthestringkjassd", "thisisthestringANDTHISISNOT");
        Assert.assertNotNull(result);
        String resultString = "";
        for(char[] i : result) {
            resultString = String.valueOf(i);
            break;
        }
        Assert.assertEquals(resultString, "thisisthestring");

        result = answerMerger.mergeAnswers("THISISNOTANDthisisthestring", "ajlsklthisisthestringkjassd");
        Assert.assertNotNull(result);
        resultString = "";
        for(char[] i : result) {
            resultString = String.valueOf(i);
            break;
        }
        Assert.assertEquals(resultString, "thisisthestring");

        result = answerMerger.mergeAnswers("kjhisdkashdskthisisthestring&*^&^kjdnsathiskjdnasthisisthestrin",
                "ajlsklthisisthestringkjassd");
        Assert.assertNotNull(result);
        resultString = "";
        for(char[] i : result) {
            resultString = String.valueOf(i);
            break;
        }
        Assert.assertEquals(resultString, "thisisthestring");

        result = answerMerger.mergeAnswers("thisisthestring", "thisisthestring");
        Assert.assertNotNull(result);
        resultString = "";
        for(char[] i : result) {
            resultString = String.valueOf(i);
            break;
        }
        Assert.assertEquals(resultString, "thisisthestring");
    }
}