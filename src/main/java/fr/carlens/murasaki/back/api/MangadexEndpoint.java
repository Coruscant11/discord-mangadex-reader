package fr.carlens.murasaki.back.api;

import org.apache.http.client.utils.URIBuilder;

public enum MangadexEndpoint {
    API("api.mangadex.org"),
    AUTH_LOGIN("auth/login"),
    MANGA("manga"),
    AT_HOME("at-home/server");

    private String url;

    MangadexEndpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return getUrl();
    }

    public static URIBuilder apiUrl() {
        var url = new URIBuilder();
        url.setScheme("https");
        url.setHost(API.toString());
        return url;
    }
}
