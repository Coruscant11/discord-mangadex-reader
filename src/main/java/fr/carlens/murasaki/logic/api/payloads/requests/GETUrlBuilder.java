package fr.carlens.murasaki.logic.api.payloads.requests;

import java.net.URISyntaxException;

public interface GETUrlBuilder {
    public String buildUrl() throws URISyntaxException;
}
