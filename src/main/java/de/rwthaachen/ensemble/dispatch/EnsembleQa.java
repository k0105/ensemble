package de.rwthaachen.ensemble.dispatch;

import de.rwthaachen.ensemble.backend.WebQaSystem;
import de.rwthaachen.ensemble.backend.YodaQaSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fp on 5/30/16.
 */
public class EnsembleQa extends Ensemble {

    Map<String,Integer> map;

    public void init() {
        ensembleMembers.add(new YodaQaSystem("Yoda"));

        ensembleMembers.add(new WebQaSystem("Evi",    1.1d));
        ensembleMembers.add(new WebQaSystem("Google", 1.2d));
        ensembleMembers.add(new WebQaSystem("Kngine", 1.3d));
        ensembleMembers.add(new WebQaSystem("Start",  1.4d));
        ensembleMembers.add(new WebQaSystem("Wolfram",1.5d));
        // Note: Removed discontinued Watson QA API, but should build own replacement with Retrieve & Rank and NLC
    }
}
