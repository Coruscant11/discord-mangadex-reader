package fr.carlens.murasaki.back.api.wrapper.responses;

import fr.carlens.murasaki.back.api.wrapper.models.Manga;

import java.util.List;

public class MangaListResponse {

    private String result;
    private String response;
    private List<Manga> data;
    private int limit;
    private int offset;
    private int total;

    public MangaListResponse() {}

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

    public List<Manga> getData() {
        return data;
    }

    public void setData(List<Manga> data) {
        this.data = data;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
