package fr.carlens.murasaki.back.api.wrapper.requests;

import fr.carlens.murasaki.back.api.MangadexEndpoint;

import java.net.URISyntaxException;

public class ChapterAtHomeRequest implements GETUrlBuilder {

    private String chapterId;

    public ChapterAtHomeRequest() {}

    public ChapterAtHomeRequest(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String buildUrl() throws URISyntaxException {
        var url = MangadexEndpoint.apiUrl();
        url.setPath("/" + MangadexEndpoint.AT_HOME + "/" + this.chapterId);
        return url.build().toString();
    }
}
