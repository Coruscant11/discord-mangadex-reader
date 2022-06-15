package fr.carlens.murasaki.back.api.wrapper.requests;

import fr.carlens.murasaki.back.api.MangadexEndpoint;

import java.net.URISyntaxException;

public class MangaAggregateRequest implements GETUrlBuilder {
    private String id;

    public MangaAggregateRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String buildUrl() throws URISyntaxException {
        var url = MangadexEndpoint.apiUrl();
        url.setPath("/" + MangadexEndpoint.MANGA + "/" + this.id + "/aggregate");
        return url.build().toString();
    }
}
