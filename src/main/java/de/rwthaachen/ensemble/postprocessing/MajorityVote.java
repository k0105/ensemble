package de.rwthaachen.ensemble.postprocessing;

import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by fp on 5/12/16.
 */
public class MajorityVote extends Strategy {

    @Override
    public String execute(List<AnswerCandidate> answerCandidateList) {
        return answerCandidateList.stream()//.filter(p -> p.getName() != null)
                .collect(Collectors.groupingBy(AnswerCandidate::getName, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue)).toString();
                //.ifPresent(System.out::println);
    }
}
