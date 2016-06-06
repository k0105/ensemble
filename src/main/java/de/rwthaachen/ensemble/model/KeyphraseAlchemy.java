package de.rwthaachen.ensemble.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fp on 6/6/16.
 */
public class KeyphraseAlchemy {
    public class KeyphraseEntry{
        String relevance;
        String text;
    }

    String status;
    String usage;
    String totalTransactions;
    String language;
    KeyphraseEntry[] keywords;

    public String getStatus() {
        return status;
    }

    public String getUsage() {
        return usage;
    }

    public String getTotalTransactions() {
        return totalTransactions;
    }

    public String getLanguage() {
        return language;
    }

    public KeyphraseEntry[] getKeywords() {
        return keywords;
    }

    public String getKeywordString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(KeyphraseEntry i : keywords) {
            stringBuilder.append(i.text);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public List<AnswerCandidate> getKeywordList() {
        List<AnswerCandidate> answers = new LinkedList<>();
        for(KeyphraseEntry i : keywords) {
            answers.add(new AnswerCandidate(i.text, Double.parseDouble(i.relevance)));
        }
        return answers;
    }
}
