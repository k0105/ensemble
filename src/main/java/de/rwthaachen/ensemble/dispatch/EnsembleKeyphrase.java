package de.rwthaachen.ensemble.dispatch;

import de.rwthaachen.ensemble.backend.KeyphraseAlchemy;
import de.rwthaachen.ensemble.backend.KeyphraseRake;
import de.rwthaachen.ensemble.backend.WebQaSystem;
import de.rwthaachen.ensemble.backend.YodaQaSystem;

/**
 * Created by fp on 6/3/16.
 */
public class EnsembleKeyphrase extends Ensemble {
    // http://localhost:4124/keyphrase/extract?plaintext=...
    // http://127.0.0.1:9093/ask?text=...
    public void init() {
        ensembleMembers.add(new KeyphraseRake("RAKE", 1.6d, "http://localhost:4124/keyphrase/extract?plaintext="));
        ensembleMembers.add(new KeyphraseAlchemy("Alchemy", 1.7d, "http://127.0.0.1:9093/ask?text="));
    }
}
