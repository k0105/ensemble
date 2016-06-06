package de.rwthaachen.ensemble.orchestration;

import de.rwthaachen.ensemble.backend.KeyphraseRake;
import de.rwthaachen.ensemble.dispatch.Dispatcher;
import de.rwthaachen.ensemble.dispatch.Ensemble;
import de.rwthaachen.ensemble.dispatch.EnsembleKeyphrase;
import de.rwthaachen.ensemble.model.AnswerCandidate;
import de.rwthaachen.ensemble.persistence.FileManager;
import de.rwthaachen.ensemble.postprocessing.Filter;
import de.rwthaachen.ensemble.postprocessing.ListMerger;
import de.rwthaachen.ensemble.postprocessing.Ranker;

import java.util.*;

/**
 * Created by fp on 6/3/16.
 */
public class PipelineKeyphrase extends Pipeline {

    Dispatcher dispatcher = new Dispatcher();

    Ensemble ensemble = new EnsembleKeyphrase();

    ListMerger listMerger = new ListMerger();

    @Override
    public String ask(String question) {
        System.out.println("Dispatching question");
        List<List<AnswerCandidate>> listOfAnswerLists = dispatcher.askQuestion(question, ensemble);

        List<AnswerCandidate> answers = listMerger.mergeResultLists(listOfAnswerLists);
        System.out.println("Merging question");
        String result = listMerger.mergeListToString(answers);

        System.out.println(result);
        return result;
    }


    public String ask2(String question) {
        System.out.println("Dispatching question");
        List<List<AnswerCandidate>> listOfAnswerLists = dispatcher.askQuestion(question, ensemble);

        // Put each best answer into HashSet, then query in order: Google, Kngine, Evi, Wolfram, Start, Yoda (by prec.)
        System.out.println("Sorting answers...");
        Map<String, String> map = new HashMap<>();
        for (List<AnswerCandidate> list : listOfAnswerLists) {
            if (list != null && list.size() > 0) {
                AnswerCandidate answerCandidate = list.get(0);
                map.put(answerCandidate.getOrigin(), answerCandidate.getName());
            }
        }

        System.out.println("Keyphrases retrieved. Postprocessing...");

        String result = null;
        Set<String> answerSet = new HashSet<>();
        String answer = map.get("RAKE");

        if (answer != null && !answer.equals("")) {
            answerSet.add(answer);
            result = answer;
        }

        return result;
    }
}
