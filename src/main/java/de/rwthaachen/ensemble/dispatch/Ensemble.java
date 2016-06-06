package de.rwthaachen.ensemble.dispatch;

import de.rwthaachen.ensemble.backend.Backend;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fp on 5/12/16.
 */
public abstract class Ensemble {

    List<Backend> ensembleMembers = new LinkedList<>();

    public Ensemble () {
        init();
    }

    public abstract void init();

    public int getMemberCount() {
        return ensembleMembers.size();
    }

    public List<Backend> getEnsembleMembers() {
        return ensembleMembers;
    }
    public void setEnsembleMembers(List<Backend> ensembleMembers) {
        this.ensembleMembers = ensembleMembers;
    }

}
