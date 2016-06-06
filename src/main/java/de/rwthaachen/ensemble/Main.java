package de.rwthaachen.ensemble;

import de.rwthaachen.ensemble.communication.ThriftServer;
import de.rwthaachen.ensemble.communication.RestServer;
import de.rwthaachen.ensemble.orchestration.Controller;
import de.rwthaachen.ensemble.orchestration.Pipeline;
import de.rwthaachen.ensemble.orchestration.PipelineQa;

/**
 * This Main class creates an ThriftServer that uses an ThriftHandler to query the ensemble
 * The handler asks the Dispatcher for an answer, which collects answers from all backends that are afterwards
 * merged by the ListMerger using a specific Strategy like a MajorityVote
 * Finally, all question/answer sets are persisted by the Database Manager
 *
 * Alternatively, a Jetty-based RESTful interface can be used
 *
 * To use: "docker-compose up" to start the data backends, web qa and YodaQA, then launch this ensemble
 * Query via Python client, which sends a question and displays an answer
 * Soon this ensemble will also be usable directly from Lucida
 *
 */
public class Main {
    private static RestServer restServer;
    private static boolean launchRest = false;

    private static Controller controller;

    // ./gradlew runRestBackend -Dde.rwthaachen.ensemble.webqaurl="http://127.0.0.1:4000"
    //  -Dde.rwthaachen.ensemble.yodaqaurl="http://127.0.0.1:4567"
    //  -Dwordnet.database.dir=/home/fp/Downloads/WordNet-3.0/dict

    // ./gradlew runThriftBackend -Dde.rwthaachen.ensemble.webqaurl="http://127.0.0.1:4000"
    //  -Dde.rwthaachen.ensemble.yodaqaurl="http://127.0.0.1:4567"
    //  -Dwordnet.database.dir=/home/fp/Downloads/WordNet-3.0/dict

    public static void main(String[] args) throws Exception {

        controller = new Controller();

        System.out.println("Command line arguments:");
        for (String s: args) {
            System.out.println(s);
            if(s.toLowerCase().equals("rest")) {
                launchRest = true;
            }
        }
        if(launchRest) {
            restServer = new RestServer();
            restServer.start(controller);
        }


        ThriftServer.setup(controller);
    }
}
