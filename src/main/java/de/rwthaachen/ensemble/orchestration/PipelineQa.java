package de.rwthaachen.ensemble.orchestration;

import de.rwthaachen.ensemble.dispatch.Dispatcher;
import de.rwthaachen.ensemble.dispatch.Ensemble;
import de.rwthaachen.ensemble.dispatch.EnsembleQa;
import de.rwthaachen.ensemble.model.AnswerCandidate;
import de.rwthaachen.ensemble.persistence.FileManager;
import de.rwthaachen.ensemble.postprocessing.ListMerger;

import java.util.*;

/**
 * The pipeline orchestrates the ensemble processing including setup, dispatch, merging, filtering and ranking
 * It is static in assuming the same stage ordering without branching
 * If more complex pipelines should ever be investigated, consider the Apache Commons Pipeline workflow framework
 * or the Chain of Responsibility pattern (cmp. GoF)
 */
public class PipelineQa extends Pipeline {

    Dispatcher dispatcher = new Dispatcher();
    // Ranker ranker = new Ranker();
    ListMerger listMerger = new ListMerger();
    // Filter filter = new Filter();
    FileManager fileManager = new FileManager();
    Ensemble ensemble = new EnsembleQa();

    public void PipelineQa() {}

    public String askFullList(String question) {
        System.out.println("Dispatching question");
        List<List<AnswerCandidate>> listOfAnswerLists = dispatcher.askQuestion(question, ensemble);
        fileManager.writeToFile(listOfAnswerLists);
        List<AnswerCandidate> answers = listMerger.mergeResultLists(listOfAnswerLists);
        System.out.println("Merging question");
        String result = listMerger.mergeListToString(answers);
        System.out.println(result);
        return result;
    }

    @Override
    public String ask(String question) {
        System.out.println("Dispatching question");
        List<List<AnswerCandidate>> listOfAnswerLists = dispatcher.askQuestion(question, ensemble);

        System.out.println("Answers retrieved. Persisting answers...");
        fileManager.writeToFile(listOfAnswerLists);

        // Put each best answer into HashSet, then query in order: Google, Kngine, Evi, Wolfram, Start, Yoda (by prec.)
        System.out.println("Sorting answers...");
        Map<String, String> map = new HashMap<>();
        for (List<AnswerCandidate> list : listOfAnswerLists) {
            if (list != null && list.size() > 0) {
                AnswerCandidate answerCandidate = list.get(0);
                map.put(answerCandidate.getOrigin(), answerCandidate.getName());
            }
        }

        System.out.println("Answers retrieved. Postprocessing...");

        String result = null;
        Set<String> answerSet = new HashSet<>();
        String answer = map.get("Google");

        if (answer != null && !answer.equals("")) {
            answerSet.add(answer);
            result = answer;
        }
        answer = map.get("Kngine");
        if (answer != null && !answer.equals("")) {
            answerSet.add(answer);
            // Only set result if it hasn't been filled, yet
            if (result == null) {
                result = answer;
            }
        }
        answer = map.get("Evi");
        if (answer != null && !answer.equals("")) {
            answerSet.add(answer);
            if (result == null) {
                result = answer;
            }
        }
        answer = map.get("Wolfram");
        if (answer != null && !answer.equals("")) {
            answerSet.add(answer);
            if (result == null) {
                result = answer;
            }
        }
        answer = map.get("Start");
        if (answer != null && !answer.equals("")) {
            answerSet.add(answer);
            if (result == null) {
                result = answer;
            }
        }
        answer = map.get("Bing");
        if (answer != null && !answer.equals("")) {
            answerSet.add(answer);
            if (result == null) {
                result = answer;
            }
        }
        answer = map.get("Yoda");
        answerSet.add(answer);
        if (result == null) {
            result = answer;
        }

        //Write set of answers into one line in dedicated file
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : answerSet) {
            stringBuilder.append(s);
            stringBuilder.append("\t");
        }
        fileManager.appendToFile("storage/Log.txt", stringBuilder.toString().replaceAll("\t$","\n"));

        return result;
    }
}
