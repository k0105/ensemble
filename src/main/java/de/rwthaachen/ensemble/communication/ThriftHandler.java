package de.rwthaachen.ensemble.communication;

import de.rwthaachen.ensemble.model.AnswerCandidate;
import de.rwthaachen.ensemble.orchestration.Controller;
import de.rwthaachen.ensemble.orchestration.Pipeline;
// Java packages
import java.util.List;
import java.io.File;
import java.util.ArrayList;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

public class ThriftHandler {
    public static void print(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }

    public static class SyncThriftHandler extends Handler implements LucidaService.Iface {
        Controller controller;
        public SyncThriftHandler(Controller controller) {
            this.controller = controller;
        }

        @Override
        public void create(String LUCID, QuerySpec spec) throws TException {
            print("Create");
        }

        @Override
        public void learn(String LUCID, QuerySpec knowledge) throws TException {
            print("learn");
        }

        @Override
        public String infer(String LUCID, QuerySpec query) throws TException {
            if (query.content.isEmpty() || query.content.get(0).data.isEmpty()) {
                throw new IllegalArgumentException();
            }
            System.out.println("Received question: " + query.content.get(0).data.get(0));
            return controller.getQaPipe().ask(query.content.get(0).data.get(0));
        }
    }

    public static class AsyncThriftHandler implements LucidaService.AsyncIface {
        private SyncThriftHandler handler;

        public AsyncThriftHandler(Controller controller) {
            handler = new SyncThriftHandler(controller);
        }

        @Override
        public void create(String LUCID, QuerySpec spec, AsyncMethodCallback resultHandler)
                throws TException {
            print("Async Create");
            handler.create(LUCID, spec);
            resultHandler.onComplete(null);
        }

        @Override
        public void learn(String LUCID, QuerySpec knowledge, AsyncMethodCallback resultHandler)
                throws TException {
            print("Async Learn");
            handler.learn(LUCID, knowledge);
            resultHandler.onComplete(null);
        }

        @Override
        public void infer(String LUCID, QuerySpec query, AsyncMethodCallback resultHandler)
                throws TException {
            print("Async Infer");
            resultHandler.onComplete(handler.infer(LUCID, query));
        }
    }
}
