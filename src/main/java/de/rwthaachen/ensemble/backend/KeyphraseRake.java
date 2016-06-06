package de.rwthaachen.ensemble.backend;

import de.rwthaachen.ensemble.communication.UrlManager;
import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fp on 6/3/16.
 */
public class KeyphraseRake extends BackendWeb {

    public KeyphraseRake(String backend) {
        super(backend);
    }

    public KeyphraseRake(String backend, double fakeConfidence) {
        super(backend, fakeConfidence);
    }

    public KeyphraseRake(String backend, double fakeConfidence, String url) {
        super(backend, fakeConfidence, url);
    }

    @Override
    public List<AnswerCandidate> parseIntoList(String output) {
        List<AnswerCandidate> answers = new LinkedList<>();

        String[] pairs = output.replaceAll("[\\[\\]\"\\\\]", "").split(",");
        for (String i : pairs) {
            String[] entry = i.split(":");
            System.out.println(entry[0] + " with confidence " + entry[1] + "\n");
            AnswerCandidate answer = new AnswerCandidate(entry[0], Double.parseDouble(entry[1]), this.getBackend());
            answers.add(answer);
        }

        return answers;
    }

}
