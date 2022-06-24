package fr.carlens.murasaki.logic.api.payloads.requests;

import fr.carlens.murasaki.logic.api.MangadexEndpoint;

import java.net.URISyntaxException;

public class SearchMangaRequest implements GETUrlBuilder {

    private String title;
    private int limit;

    public SearchMangaRequest(String title, int limit) {
        this.title = title;
        this.limit = limit;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


    @Override
    public String buildUrl() throws URISyntaxException {
        var url = MangadexEndpoint.apiUrl();
        url.setPath("/" + MangadexEndpoint.MANGA);
        url.addParameter("title", this.title);
        url.addParameter("limit", String.valueOf(this.limit));
        return url.build().toString();
    }
}
