package fr.carlens.murasaki.back.api.wrapper.models;

public class Token {

    private String session;
    private String refresh;

    public Token() {}

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
