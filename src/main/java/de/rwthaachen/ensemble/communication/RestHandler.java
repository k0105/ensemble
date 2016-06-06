package de.rwthaachen.ensemble.communication;

import de.rwthaachen.ensemble.backend.WordNetLookup;
import de.rwthaachen.ensemble.orchestration.Controller;
import de.rwthaachen.ensemble.orchestration.Pipeline;
import de.rwthaachen.ensemble.utilities.SimilarityScore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by fp on 5/31/16.
 */
@Path("ensemble")
public class RestHandler {

    private Controller controller;
    private WordNetLookup wnl;
    private SimilarityScore similarityScore;

    public RestHandler(Controller controller) {
        this.controller = controller;
        wnl = new WordNetLookup();
        similarityScore = new SimilarityScore();
    }

    @GET
    @Path("sim")
    @Produces(MediaType.TEXT_PLAIN)
    public String similarities(@QueryParam("s1") String s1, @QueryParam("s2") String s2) {
        // http://localhost:9091/ensemble/sim?s1=One%20rather%20long%20example%20stringy%20test&s2=One%20long%20string
        return similarityScore.similaritiesVerbose(s1, s2);
    }

    @GET
    @Path("ask")
    @Produces(MediaType.TEXT_PLAIN)
    public String ask(@QueryParam("question") String question) {
        // http://localhost:9091/ensemble/ask?question=Question
        return controller.getQaPipe().ask(question);
    }

    @GET
    @Path("key")
    @Produces(MediaType.TEXT_PLAIN)
    public String askKey(@QueryParam("input") String input) {
        return controller.getKeyPipe().ask(input);
    }

    @GET
    @Path("wordnet")
    @Produces(MediaType.TEXT_PLAIN)
    public String wordnetLookup(@QueryParam("word") String word) {
        // http://localhost:9091/ensemble/wordnet?word=Question
        return wnl.lookup(word);
    }
}
