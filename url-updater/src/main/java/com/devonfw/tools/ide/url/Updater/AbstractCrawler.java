package com.devonfw.tools.ide.url.Updater;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class AbstractCrawler {
    protected HttpClient client = HttpClient.newBuilder().build();
    protected String doGetResponseBody(String url){
        try {
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            return client.send(request1, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException exception){
            throw new IllegalStateException("Failed to retrieve response body from url: " + url, exception);
        }

    }
}
