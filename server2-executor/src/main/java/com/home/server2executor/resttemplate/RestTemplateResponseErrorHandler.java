package com.home.server2executor.resttemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
/**
 * Error handler that ignores client errors
 * and provides ability to handle it manually.
 */
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse)
            throws IOException {
        return (clientHttpResponse.getStatusCode().series()
                == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse)
            throws IOException {
        if (clientHttpResponse.getStatusCode().series()
                == HttpStatus.Series.SERVER_ERROR) {
            throw new HttpServerErrorException(clientHttpResponse.getStatusCode());
        }
    }
}
