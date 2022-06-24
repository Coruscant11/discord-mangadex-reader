package fr.carlens.murasaki.back.api.wrapper.responses;

import fr.carlens.murasaki.back.api.wrapper.models.ChapterAtHome;

public class ChapterAtHomeResponse {

    private String result;
    private String baseUrl;
    private ChapterAtHome chapter;

    public ChapterAtHomeResponse() {}

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ChapterAtHome getChapter() {
        return chapter;
    }

    public void setChapter(ChapterAtHome chapter) {
        this.chapter = chapter;
    }
}
