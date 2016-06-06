package de.rwthaachen.ensemble.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.rwthaachen.ensemble.model.AnswerCandidate;
import org.testng.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fp on 6/6/16.
 */
public class KeyphraseAlchemy extends BackendWeb {

    public KeyphraseAlchemy(String backend) {
        super(backend);
    }

    public KeyphraseAlchemy(String backend, double fakeConfidence) {
        super(backend, fakeConfidence);
    }

    public KeyphraseAlchemy(String backend, double fakeConfidence, String url) {
        super(backend, fakeConfidence, url);
    }

    @Override
    public List<AnswerCandidate> parseIntoList(String output) {
        Gson gson = new GsonBuilder().create();
        de.rwthaachen.ensemble.model.KeyphraseAlchemy results = gson.fromJson(output,
                de.rwthaachen.ensemble.model.KeyphraseAlchemy.class);
        return results.getKeywordList();
    }

}
