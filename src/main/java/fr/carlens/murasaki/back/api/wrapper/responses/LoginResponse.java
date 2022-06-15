package fr.carlens.murasaki.back.api.wrapper.responses;

import fr.carlens.murasaki.back.api.wrapper.models.Token;

public class LoginResponse {

    private String result;
    private Token token;

    public LoginResponse() {}

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
