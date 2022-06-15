package fr.carlens.murasaki.back.api.wrapper.responses;

import fr.carlens.murasaki.back.api.wrapper.models.VolumeAggregate;
import fr.carlens.murasaki.back.api.wrapper.requests.MangaAggregateRequest;

import java.util.List;
import java.util.Map;

public class MangaAggregateResponse {

    private String result;
    private Map<String, VolumeAggregate> volumes;

    public MangaAggregateResponse() {}

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, VolumeAggregate>  getVolumes() {
        return volumes;
    }

    public void setVolumes(Map<String, VolumeAggregate>  volumes) {
        this.volumes = volumes;
    }
}
