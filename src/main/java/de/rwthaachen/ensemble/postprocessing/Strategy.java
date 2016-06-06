package de.rwthaachen.ensemble.postprocessing;

import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.util.List;

/**
 * Created by fp on 5/12/16.
 */
public abstract class Strategy {

    public abstract String execute(List<AnswerCandidate> answerCandidateList);
}
