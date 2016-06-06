package de.rwthaachen.ensemble.backend;

import de.rwthaachen.ensemble.communication.UrlManager;
import de.rwthaachen.ensemble.model.AnswerCandidate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fp on 6/6/16.
 */
public abstract class BackendWeb extends Backend {
    public BackendWeb() {}

    public BackendWeb(String backend) {
        this.backend = backend;
    }

    public BackendWeb(String backend, double fakeConfidence) {
        this.backend = backend;
        this.fakeConfidence = fakeConfidence;
    }

    public BackendWeb(String backend, double fakeConfidence, String url) {
        this.backend = backend;
        this.fakeConfidence = fakeConfidence;
        this.url = url;
    }

    private double fakeConfidence = 1.0d;
    public double getFakeConfidence() {
        return fakeConfidence;
    }
    public void setFakeConfidence(double fakeConfidence) {
        this.fakeConfidence = fakeConfidence;
    }

    private String url = null;

    @Override
    public List<AnswerCandidate> askQuestion(String question) throws IOException {

        List<AnswerCandidate> answers = null;

        try {
            String baseURL;
            if (url == null) {
                baseURL = UrlManager.lookUpUrl(UrlManager.Backends.WEBQA.ordinal()) + "/qa/ask"
                        + backend + "?question=";
            } else {
                baseURL = url;
            }
            StringBuilder result = new StringBuilder();
            URL url = new URL(baseURL + URLEncoder.encode(question, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            String answerText = result.toString();

            answers = parseIntoList(answerText);

        } catch (ProtocolException pe) {
            pe.printStackTrace();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return answers;
    }

    public abstract List<AnswerCandidate> parseIntoList(String output);
    //            if(answerText != null && !answerText.equals("")) {
//                AnswerCandidate answer = new AnswerCandidate(answerText, fakeConfidence, getBackend());
//                answers.add(answer);
//            }
}
