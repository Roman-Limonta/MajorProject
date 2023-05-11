package com.teamlimonta.majorproject;

import java.time.LocalDate;

public class Projects {
    private String name;
    private final String details;
    private final String protagonist;
    private final String questionTool;
    private final String heartOfDarkness;
    private final LocalDate dateCreated;


    public Projects(String name, String details, String protagonist, String questionTool, String heartOfDarkness) {
        this.name = name;
        this.details = details;
        this.protagonist = protagonist;
        this.questionTool = questionTool;
        this.heartOfDarkness = heartOfDarkness;
        this.dateCreated = LocalDate.now();
    }

    public Projects(String name, String details, String protagonist,String questionTool, String heartOfDarkness, LocalDate dateCreated) {
        this.name = name;
        this.details = details;
        this.protagonist = protagonist;
        this.questionTool = questionTool;
        this.heartOfDarkness = heartOfDarkness;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public String getProtagonist() {
        return protagonist;
    }

    public String getQuestionTool() {
        return questionTool;
    }

    public String getHeartOfDarkness() {
        return heartOfDarkness;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return name ;
    }

}
