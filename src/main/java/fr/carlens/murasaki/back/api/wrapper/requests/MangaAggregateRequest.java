package fr.carlens.murasaki.back.api.wrapper.requests;

import fr.carlens.murasaki.back.api.MangadexEndpoint;

import java.net.URISyntaxException;

public class MangaAggregateRequest implements GETUrlBuilder {
    private String id;
    private String language;

    public MangaAggregateRequest(String id, String language) {
        this.id = id;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String buildUrl() throws URISyntaxException {
        var url = MangadexEndpoint.apiUrl();
        url.setPath("/" + MangadexEndpoint.MANGA + "/" + this.id + "/aggregate");
        url.addParameter("translatedLanguage[]", this.language);
        return url.build().toString();
    }
}
