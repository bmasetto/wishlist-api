package com.company.wishlist.adapter.output.productrestclient.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    public static final List<HttpStatus> ACCEPTED_HTTP_ERRORS = Collections.singletonList(
            HttpStatus.NOT_FOUND
    );

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (ACCEPTED_HTTP_ERRORS.contains(clientHttpResponse.getStatusCode())) {
            return false;
        }

        return clientHttpResponse.getStatusCode().is4xxClientError() ||
                clientHttpResponse.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (ACCEPTED_HTTP_ERRORS.contains(clientHttpResponse.getStatusCode())) {
            return;
        }

        super.handleError(clientHttpResponse);
    }
}
