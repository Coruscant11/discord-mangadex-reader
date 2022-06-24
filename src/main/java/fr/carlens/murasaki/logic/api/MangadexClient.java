package fr.carlens.murasaki.logic.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.carlens.murasaki.logic.api.models.Manga;
import fr.carlens.murasaki.logic.api.payloads.requests.*;
import fr.carlens.murasaki.logic.api.payloads.responses.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class MangadexClient {

    public static void main(String[] args) throws IOException {

    }

    private HttpClient client;
    private ObjectMapper om;

    public MangadexClient() {
        this.client = HttpClients.createDefault();
        this.om = new ObjectMapper();
        this.om.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        this.om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public MangadexClient(String username, String password) {}

    private HttpResponse sendGetRequest(String url) throws IOException {
        HttpGet request = new HttpGet(url);

        return client.execute(request);
    }

    public List<Manga> searchManga(SearchMangaRequest smr) throws APIException, IOException, URISyntaxException {
        HttpResponse response = sendGetRequest(smr.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            MangaListResponse mangaListResponse = om.readValue(bodyResponse, MangaListResponse.class);
            if (mangaListResponse.getResult().equalsIgnoreCase("ok"))
                return mangaListResponse.getData();
        }

        throw new APIException(statusCode, bodyResponse);
    }

    public MangaResponse getManga(MangaRequest mr) throws APIException, IOException, URISyntaxException {
        HttpResponse response = sendGetRequest(mr.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            return om.readValue(bodyResponse, MangaResponse.class);
        }

        throw new APIException(statusCode, bodyResponse);
    }

    public MangaResponse getRandomManga() throws APIException, IOException, URISyntaxException {
        MangaRandomRequest mrr = new MangaRandomRequest();
        HttpResponse response = sendGetRequest(mrr.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
             return om.readValue(bodyResponse, MangaResponse.class);
        }

        throw new APIException(statusCode, bodyResponse);
    }

    public MangaAggregateResponse getMangaAggregate(MangaAggregateRequest mar) throws APIException, IOException, URISyntaxException {
        HttpResponse response = sendGetRequest(mar.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            return om.readValue(bodyResponse, MangaAggregateResponse.class);
        }

        throw new APIException(statusCode, bodyResponse);
    }

    public ChapterResponse getChapter(ChapterRequest cr)  throws APIException, IOException, URISyntaxException {
        HttpResponse response = sendGetRequest(cr.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            return om.readValue(bodyResponse, ChapterResponse.class);
        }

        throw new APIException(statusCode, bodyResponse);
    }

    public ChapterAtHomeResponse getChapterAtHome(ChapterAtHomeRequest cahr) throws APIException, IOException, URISyntaxException {
        HttpResponse response = sendGetRequest(cahr.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            return om.readValue(bodyResponse, ChapterAtHomeResponse.class);
        }

        throw new APIException(statusCode, bodyResponse);
    }
}
