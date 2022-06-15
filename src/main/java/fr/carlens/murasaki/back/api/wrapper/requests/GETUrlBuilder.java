package fr.carlens.murasaki.back.api.wrapper.requests;

import java.net.URISyntaxException;

public interface GETUrlBuilder {
    public String buildUrl() throws URISyntaxException;
}
