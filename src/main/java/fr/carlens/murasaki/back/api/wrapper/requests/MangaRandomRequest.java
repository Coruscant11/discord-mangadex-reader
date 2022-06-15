package fr.carlens.murasaki.back.api.wrapper.requests;

import fr.carlens.murasaki.back.api.MangadexEndpoint;

import java.net.URISyntaxException;

public class MangaRandomRequest implements GETUrlBuilder {
    private String id = "random";

    public MangaRandomRequest() {}


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
        url.addParameter("contentRating[]", "safe");
        return url.build().toString();
    }
}
