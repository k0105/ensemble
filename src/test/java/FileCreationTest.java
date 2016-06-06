import de.rwthaachen.ensemble.model.AnswerCandidate;
import de.rwthaachen.ensemble.persistence.FileManager;
import de.rwthaachen.ensemble.postprocessing.AnswerMerger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fp_work on 22.12.15.
 */
public class FileCreationTest {
    AnswerMerger answerMerger;
    FileManager fileManager;

    @BeforeSuite
    public void beforeTest() {
        fileManager = new FileManager();
    }

    @Test()
    public void testAuthorIdExtraction() {
        List<List<AnswerCandidate>> list = new LinkedList<>();
        List<AnswerCandidate> subList1 = new LinkedList<>();
        subList1.add(new AnswerCandidate("1",1.1d,"test"));
        subList1.add(new AnswerCandidate("2",1.2d,"test"));
        List<AnswerCandidate> subList2 = new LinkedList<>();
        subList2.add(new AnswerCandidate("3",1.3d,"test2"));
        subList2.add(new AnswerCandidate("4",1.4d,"test2"));
        list.add(subList1);
        list.add(subList2);

        fileManager.writeToFile(list);
    }
}