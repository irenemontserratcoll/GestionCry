package com.example.restapi.client;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(@SuppressWarnings("null") ClientHttpResponse response) throws IOException {
        return false; // No consider any response as error
    }

    @Override
    public void handleError(@SuppressWarnings("null") ClientHttpResponse response) throws IOException {
        // Do nothing, let the calling code handle the response
    }
}
