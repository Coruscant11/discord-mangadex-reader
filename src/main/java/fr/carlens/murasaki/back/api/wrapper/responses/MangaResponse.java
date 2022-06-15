package fr.carlens.murasaki.back.api.wrapper.responses;

import fr.carlens.murasaki.back.api.wrapper.models.Manga;

public class MangaResponse {

    private String result;
    private String response;
    private Manga data;

    public MangaResponse() {}

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Manga getData() {
        return data;
    }

    public void setData(Manga data) {
        this.data = data;
    }
}
