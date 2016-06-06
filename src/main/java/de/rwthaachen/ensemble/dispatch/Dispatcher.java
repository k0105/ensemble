package de.rwthaachen.ensemble.dispatch;

import de.rwthaachen.ensemble.backend.Backend;
import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by fp on 5/12/16.
 */
public class Dispatcher {

    public Dispatcher() {}

    public List<List<AnswerCandidate>> askQuestion(String question, Ensemble ensemble) {
        List<List<AnswerCandidate>> resultLists = new LinkedList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CompletionService<List<AnswerCandidate>> taskCompletionService =
                new ExecutorCompletionService<>(executorService);

        List<Callable<List<AnswerCandidate>>> callables = new ArrayList<>();

        try {
            if (ensemble == null) {
                return null;
            }

            // Add each active system in ensemble to callables list
            for (Backend qas : ensemble.getEnsembleMembers()) {
                if (qas.isActive()) {
                    qas.setInput(question);
                    callables.add(qas);
                }
            }

            // Submit each callable (could probably also have been done in previous loop)
            for (Callable<List<AnswerCandidate>> callable : callables) {
                taskCompletionService.submit(callable);
            }

            // Obtain results as they arrive
            List<AnswerCandidate> candidate;
            for (int i = 0; i < callables.size(); i++) {
                Future<List<AnswerCandidate>> result = taskCompletionService.take();
                if(result != null && (candidate = result.get()) != null) {
                    //System.out.println("Got result from " + candidate.get(0).getOrigin());
                    //System.out.println("With length " + candidate.size());
                    //resultLists[ensemble.resolveName(candidate.get(0).getOrigin())] = candidate;
                    resultLists.add(candidate);
                    //answers.addAll(candidate);
                }
            }

        } catch (InterruptedException e) {
            // no real error handling. Don't do this in production!
            e.printStackTrace();
        } catch (ExecutionException e) {
            // no real error handling. Don't do this in production!
            e.printStackTrace();
        }
        executorService.shutdown();

        System.out.println("" + resultLists.size());

        return resultLists;
    }

}
