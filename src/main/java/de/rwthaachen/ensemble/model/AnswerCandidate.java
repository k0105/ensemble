package de.rwthaachen.ensemble.model;

/**
 * Created by fp on 5/12/16.
 */
public class AnswerCandidate {

    private String name;
    private double confidence;
    private String origin;

    public AnswerCandidate (String name, double confidence) {
        this.name = name;
        this.confidence = confidence;
    }

    public AnswerCandidate (String name, double confidence, String origin) {
        this.name = name;
        this.confidence = confidence;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getConfidence() {
        return confidence;
    }
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
