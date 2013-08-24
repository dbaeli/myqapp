package com.infoq.myqapp.domain;

import java.util.Date;

public class ValidatedContent {

    private String name;
    private Date dateLastActivity;

    private String trelloUrl;
    private String githubUrl;
    private String infoqUrl;

    private boolean isArticle;
    private boolean isOriginal;
    private boolean isMentoring;

    private TrelloMember author;
    private TrelloMember mentor;
    private TrelloMember validator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateLastActivity() {
        return dateLastActivity;
    }

    public void setDateLastActivity(Date dateLastActivity) {
        this.dateLastActivity = dateLastActivity;
    }

    public String getTrelloUrl() {
        return trelloUrl;
    }

    public void setTrelloUrl(String trelloUrl) {
        this.trelloUrl = trelloUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getInfoqUrl() {
        return infoqUrl;
    }

    public void setInfoqUrl(String infoqUrl) {
        this.infoqUrl = infoqUrl;
    }

    public boolean isArticle() {
        return isArticle;
    }

    public void setArticle(boolean article) {
        this.isArticle = article;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    public boolean isMentoring() {
        return isMentoring;
    }

    public void setMentoring(boolean mentoring) {
        isMentoring = mentoring;
    }

    public TrelloMember getAuthor() {
        return author;
    }

    public void setAuthor(TrelloMember author) {
        this.author = author;
    }

    public TrelloMember getMentor() {
        return mentor;
    }

    public void setMentor(TrelloMember mentor) {
        this.mentor = mentor;
    }

    public TrelloMember getValidator() {
        return validator;
    }

    public void setValidator(TrelloMember validator) {
        this.validator = validator;
    }
}