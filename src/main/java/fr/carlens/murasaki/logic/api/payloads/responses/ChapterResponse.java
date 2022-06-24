package fr.carlens.murasaki.logic.api.payloads.responses;

import fr.carlens.murasaki.logic.api.models.Chapter;

public class ChapterResponse {
    private String result;
    private String response;
    private Chapter data;

    public ChapterResponse() {}

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

    public Chapter getData() {
        return data;
    }

    public void setData(Chapter data) {
        this.data = data;
    }
}
