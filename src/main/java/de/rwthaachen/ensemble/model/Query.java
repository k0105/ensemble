package de.rwthaachen.ensemble.model;

import javax.persistence.*;

@Entity
//@Access(AccessType.PROPERTY)
@Table(name = "T_QUERY")
public class Query {

    private String question;
    @Column(name = "QUERY")
    @Access(AccessType.PROPERTY)
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    @Id
    @GeneratedValue
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Query() {
        this.question = "";
    }

    public Query (String question) {
        this.question = question;
    }




}
