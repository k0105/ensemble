package de.rwthaachen.ensemble.communication;

import de.rwthaachen.ensemble.dispatch.Dispatcher;
import de.rwthaachen.ensemble.postprocessing.ListMerger;

/**
 * Created by fp on 5/16/16.
 */
public abstract class Handler {
    Dispatcher dispatcher;
    ListMerger listMerger;

    public Handler () {
        dispatcher = new Dispatcher();
        listMerger = new ListMerger();
    }
}
