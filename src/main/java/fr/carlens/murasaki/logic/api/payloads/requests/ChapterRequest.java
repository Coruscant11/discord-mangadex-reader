package fr.carlens.murasaki.logic.api.payloads.requests;

import fr.carlens.murasaki.logic.api.MangadexEndpoint;

import java.net.URISyntaxException;

public class ChapterRequest implements GETUrlBuilder {

    private String chapterId;

    public ChapterRequest(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String buildUrl() throws URISyntaxException {
        var url = MangadexEndpoint.apiUrl();
        url.setPath("/" + MangadexEndpoint.CHAPTER + "/" + this.chapterId);
        return url.build().toString();
    }
}
