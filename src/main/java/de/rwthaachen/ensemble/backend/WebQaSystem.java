package de.rwthaachen.ensemble.backend;

import de.rwthaachen.ensemble.model.AnswerCandidate;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by fp on 5/12/16.
 */
public class WebQaSystem extends BackendWeb {

    public WebQaSystem(String backend) {
        super(backend);
    }

    public WebQaSystem(String backend, double fakeConfidence) {
        super(backend, fakeConfidence);
    }

    public WebQaSystem(String backend, double fakeConfidence, String url) {
        super(backend, fakeConfidence, url);
    }

    @Override
    public List<AnswerCandidate> parseIntoList(String output) {
        List<AnswerCandidate> answers = new LinkedList<>();
        if (output != null && !output.equals("")) {
            AnswerCandidate answer = new AnswerCandidate(output, this.getFakeConfidence(), getBackend());
            answers.add(answer);
        }
        return answers;
    }

}
