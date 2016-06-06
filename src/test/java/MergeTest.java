import de.rwthaachen.ensemble.postprocessing.AnswerMerger;
import de.rwthaachen.ensemble.utilities.SimilarityScore;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.Set;

/**
 * Created by fp_work on 22.12.15.
 */
public class MergeTest {
    AnswerMerger answerMerger;
    SimilarityScore similarityScore;

    @BeforeSuite
    public void beforeTest() {
        answerMerger = new AnswerMerger();
        similarityScore = new SimilarityScore();
    }

    @Test()
    public void testMerge() {
        String s1 = "Umberto Eco was born in Alessandria.";
        String s2 = "Alessandria, Italy";
        String s3 = "Untitled DocumentSTART's reply img {max-width: \"100%\";}===> Where was Umberto Eco bornUmberto " +
                "EcoBirth place: Alessandria, Piedmont, ItalySource: WikipediaUmberto Eco's location of birth: " +
                "Alessandria, Piedmont, ItalySource: The Internet Movie Database";
        String s4 = "Alessandria";
        String s5 = "Alessandria, Piemonte, Italy";
        System.out.println(similarityScore.similaritiesVerbose(s1,s2));
        Set<char[]> result = answerMerger.mergeAnswers(s1,s2);
        System.out.println(""+helper(result));
        System.out.println("#########################\n#########################");
        System.out.println(similarityScore.similaritiesVerbose(s2,s3));
        result = answerMerger.mergeAnswers(s2,s3);
        System.out.println(""+helper(result));
        System.out.println("#########################\n#########################");
        System.out.println(similarityScore.similaritiesVerbose(s3,s4));
        result = answerMerger.mergeAnswers(s3,s4);
        System.out.println(""+helper(result));
        System.out.println("#########################\n#########################");
        System.out.println(similarityScore.similaritiesVerbose(s4,s5));
        result = answerMerger.mergeAnswers(s4,s5);
        System.out.println(""+helper(result));
    }

    public int helper(Set<char[]> result) {
        String resultString = "";
        for(char[] i : result) {
            resultString = String.valueOf(i);
            break;
        }
        return resultString.length();
    }
}