package de.rwthaachen.ensemble.communication;

import de.rwthaachen.ensemble.model.AnswerCandidate;
import de.rwthaachen.ensemble.orchestration.Controller;
import de.rwthaachen.ensemble.orchestration.Pipeline;
import org.apache.thrift.TException;

import java.util.List;

/**
 *
 */
public class ThriftHandler extends Handler implements LucidaService.Iface {

    Controller controller;
    public ThriftHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void create(String LUCID, QuerySpec spec) throws TException {
        System.out.println("Create");
    }

    @Override
    public void learn(String LUCID, QuerySpec knowledge) throws TException {
        System.out.println("learn");
    }

    @Override
    public String infer(String LUCID, QuerySpec query) throws TException {
        System.out.println("Received question: " + LUCID);
        return controller.getQaPipe().ask(LUCID);
    }
}
