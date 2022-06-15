package fr.carlens.murasaki.back.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.carlens.murasaki.back.api.wrapper.models.Manga;
import fr.carlens.murasaki.back.api.wrapper.models.VolumeAggregate;
import fr.carlens.murasaki.back.api.wrapper.requests.MangaAggregateRequest;
import fr.carlens.murasaki.back.api.wrapper.requests.MangaRandomRequest;
import fr.carlens.murasaki.back.api.wrapper.requests.MangaRequest;
import fr.carlens.murasaki.back.api.wrapper.requests.SearchMangaRequest;
import fr.carlens.murasaki.back.api.wrapper.responses.MangaAggregateResponse;
import fr.carlens.murasaki.back.api.wrapper.responses.MangaListResponse;
import fr.carlens.murasaki.back.api.wrapper.responses.MangaResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class MangadexClient {

    public static void main(String[] args) throws IOException {

    }

    private HttpClient client;
    private ObjectMapper om;

    public MangadexClient() {
        this.client = HttpClients.createDefault();
        this.om = new ObjectMapper();
    }

    public MangadexClient(String username, String password) {

    }

    private HttpResponse sendGetRequest(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        return response;
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

    public Manga getManga(MangaRequest mr) throws APIException, IOException, URISyntaxException {
        HttpResponse response = sendGetRequest(mr.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            MangaResponse mangaResponse = om.readValue(bodyResponse, MangaResponse.class);
            if (mangaResponse.getResult().equalsIgnoreCase("ok"))
                return mangaResponse.getData();
        }

        throw new APIException(statusCode, bodyResponse);
    }

    public Manga getRandomManga() throws APIException, IOException, URISyntaxException {
        MangaRandomRequest mrr = new MangaRandomRequest();
        System.out.println(mrr.buildUrl());

        HttpResponse response = sendGetRequest(mrr.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            MangaResponse mangaResponse = om.readValue(bodyResponse, MangaResponse.class);
            if (mangaResponse.getResult().equalsIgnoreCase("ok"))
                return mangaResponse.getData();
        }

        throw new APIException(statusCode, bodyResponse);
    }

    public Map<String, VolumeAggregate> getMangaAggregate(MangaAggregateRequest mar) throws APIException, IOException, URISyntaxException {
        System.out.println(mar.buildUrl());

        HttpResponse response = sendGetRequest(mar.buildUrl());

        String bodyResponse = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            MangaAggregateResponse mangaAggregateResponse = om.readValue(bodyResponse, MangaAggregateResponse.class);
            if (mangaAggregateResponse.getResult().equalsIgnoreCase("ok"))
                return mangaAggregateResponse.getVolumes();
        }

        throw new APIException(statusCode, bodyResponse);
    }
}
