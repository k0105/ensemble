import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.rwthaachen.ensemble.model.AnswerCandidate;
import de.rwthaachen.ensemble.postprocessing.AnswerMerger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by fp on 6/7/16.
 */
public class ThresholdTest {
    String jsonList = "[{\"name\":\"foster care system\",\"confidence\":8.5,\"origin\":\"RAKE\"},{\"name\":\"stable foster family\",\"confidence\":8.5,\"origin\":\"RAKE\"},{\"name\":\"foster mother\",\"confidence\":4.5,\"origin\":\"RAKE\"},{\"name\":\"foster cousin\",\"confidence\":4.5,\"origin\":\"RAKE\"},{\"name\":\"car accident\",\"confidence\":4.0,\"origin\":\"RAKE\"},{\"name\":\"eventually intended\",\"confidence\":4.0,\"origin\":\"RAKE\"},{\"name\":\"weak existence\",\"confidence\":4.0,\"origin\":\"RAKE\"},{\"name\":\"irish descent\",\"confidence\":4.0,\"origin\":\"RAKE\"},{\"name\":\"stable foster family\",\"confidence\":0.920579},{\"name\":\"Belle\",\"confidence\":0.744561},{\"name\":\"weak existence\",\"confidence\":0.699854},{\"name\":\"car accident\",\"confidence\":0.684776},{\"name\":\"Irish descent.\",\"confidence\":0.674136},{\"name\":\"foster cousin\",\"confidence\":0.634926},{\"name\":\"foster mother\",\"confidence\":0.628758},{\"name\":\"foster care\",\"confidence\":0.615559},{\"name\":\"succession\",\"confidence\":0.405493},{\"name\":\"Independence\",\"confidence\":0.39692},{\"name\":\"ward\",\"confidence\":0.391446},{\"name\":\"caring\",\"confidence\":0.385943},{\"name\":\"Louisiana.\",\"confidence\":0.372457},{\"name\":\"homes\",\"confidence\":0.36975},{\"name\":\"age\",\"confidence\":0.367668},{\"name\":\"state\",\"confidence\":0.366835}]";
    Gson gson;
    List<AnswerCandidate> answerCandidateList;

    @BeforeSuite
    public void beforeTest() {
        gson = new Gson();
        Type listType = new TypeToken<List<AnswerCandidate>>() {}.getType();
        answerCandidateList = new Gson().fromJson(jsonList, listType);
    }

    @Test()
    public void testAuthorIdExtraction() {

    }
}
