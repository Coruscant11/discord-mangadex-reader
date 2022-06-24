package fr.carlens.murasaki.logic.api.payloads.requests;

import fr.carlens.murasaki.logic.api.MangadexEndpoint;

import java.net.URISyntaxException;

public class MangaRequest implements GETUrlBuilder {

    private String id;

    public MangaRequest() {}

    public MangaRequest(String id) {
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
        url.setPath("/" + MangadexEndpoint.MANGA + "/" + this.id);
        return url.build().toString();
    }
}
