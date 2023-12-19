package eu.deltasource.training.library.steps;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestExecutor {

    private ResponseEntity<String> response;
    private final TestRestTemplate testRestTemplate;

    public HttpRequestExecutor (TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public ResponseEntity<String> exchange(String url, HttpMethod method) {
        response =  testRestTemplate.exchange(url, method, null, String.class);
        return response;
    }

    public ResponseEntity<String> getResponse() {
        return response;
    }
}
