package com.infoq.myqapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.scribe.model.Token;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {

    @Id
    private String email;
    private String tokenTrello;
    private String secretTrello;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Token getTokenTrello() {
        if (tokenTrello == null || secretTrello == null) {
            return null;
        }
        return new Token(tokenTrello, secretTrello);
    }

    public void setTokenTrello(Token tokenTrello) {
        this.tokenTrello = tokenTrello.getToken();
        this.secretTrello = tokenTrello.getSecret();
    }
}