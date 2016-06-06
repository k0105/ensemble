package de.rwthaachen.ensemble.backend;

import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by fp on 5/12/16.
 */
public abstract class Backend implements Callable<List<AnswerCandidate>> {

    private boolean active = true;
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    private String input = "";
    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
    }

    protected String backend = "";
    public String getBackend() {
        return backend;
    }
    public void setBackend(String backend) {
        this.backend = backend;
    }

    public Backend() {}

    public Backend(String backend) {
        this.backend = backend;
    }

    @Override
    public List<AnswerCandidate> call() {
        try {
            return askQuestion(this.getInput());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * Asks a input of QA system and return its reply in unified syntax
     *
     * To throw exception in implementing class: throw new IOException("Cause description");
     *
     * @param question  The user's input in unmodified plain text
     * @return  Answer from the QA system
     */
    public abstract List<AnswerCandidate> askQuestion(String question) throws IOException;

    /**
     * Method that is internally used to query QA backend
     * Only implement this, DO NOT CALL THIS METHOD DIRECTLY - USE INVOKECALL
     * Note: This should be done via CDI at some point
     *
     * @param input Can mean different things depending on the backend - input, ID or input code for instance
     * @return Raw answer from QA backend, usually JSON array with answers and corresponding confidences
     */
    //public abstract String invoke(String input);

    // Preparation for corpus support:
//    class CorpusEntry{
//        boolean active;
//        String name;
//
//        public CorpusEntry (String name, boolean active) {
//            this.active = active;
//            this.name = name;
//        }
//
//        public boolean isActive() {
//            return active;
//        }
//
//        public void setActive(boolean active) {
//            this.active = active;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
//    public abstract void setCorpora(List<CorpusEntry> corpusNames);
//    public abstract void setCorpus(String name, boolean targetValue);
//    public abstract List<CorpusEntry> getCorpora();

}
