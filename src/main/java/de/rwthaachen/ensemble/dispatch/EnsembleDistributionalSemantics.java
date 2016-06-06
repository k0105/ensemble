package de.rwthaachen.ensemble.dispatch;

import de.rwthaachen.ensemble.backend.WebQaSystem;

/**
 * Created by fp on 6/6/16.
 */
public class EnsembleDistributionalSemantics extends Ensemble {
    public void init() {
        ensembleMembers.add(new WebQaSystem("RAKE", 1.6d, "http://localhost:/jbt/similarTerms?term=&backend="));
        ensembleMembers.add(new WebQaSystem("RAKE", 1.6d, "http://localhost:4123/wordemb/mostsimilar?setPos="));
        // http://localhost:4123/wordemb/doesntmatch?set=cat%20dog%20horse%20rabbit%20tiger%20bus
        // http://localhost:4123/wordemb/mostsimilar?setPos=king%20woman&setNeg=man
        //
    }
}
