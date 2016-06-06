package de.rwthaachen.ensemble.postprocessing;

import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.util.List;

/**
 * Created by fp on 5/25/16.
 */
public class Ranker {
    // Could use Ranking SVM or Bluemix Retrieve & Rank, but would highly benefit from domain expert reviews
    // See "Learning to Rank Using Classification and Gradient Boosting" for additional methods

    public List<AnswerCandidate> rank(List<AnswerCandidate> list) {
        return list;
    }
}
