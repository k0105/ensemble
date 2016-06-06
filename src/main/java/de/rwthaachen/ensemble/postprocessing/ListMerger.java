package de.rwthaachen.ensemble.postprocessing;

import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by fp on 5/12/16.
 * This class takes a list of answer either merges them into one final answer or rejects to answer
 */
public class ListMerger {

    public ListMerger() {
        // Default strategy: majority vote
        strategy = new MajorityVote();
    }

    private Strategy strategy;
    public Strategy getStrategy() {
        return strategy;
    }
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    // TODO Need method to apply strategy
    // strategy.execute(answers)

    public String mergeListToString(List<AnswerCandidate> answers) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMaximumFractionDigits(2);
        StringBuilder stringBuilder = new StringBuilder();
        for (AnswerCandidate answerCandidate : answers) {
            stringBuilder.append(df.format(answerCandidate.getConfidence()));
            stringBuilder.append("\t");
            stringBuilder.append(answerCandidate.getName());
            //Double.toString(answerCandidate.getConfidence()));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public List<AnswerCandidate> mergeResultLists(List<List<AnswerCandidate>> listOfCandidateLists) {
        if (listOfCandidateLists == null) {
            return null;
        }

        List<AnswerCandidate> answers = new LinkedList<>();
        List<AnswerCandidate> candidate;
        for (int i = 0; i < listOfCandidateLists.size(); i++) {
            candidate = listOfCandidateLists.get(i);
            if (candidate != null) {
                answers.addAll(candidate);
            }
        }
        return answers;
    }
}
