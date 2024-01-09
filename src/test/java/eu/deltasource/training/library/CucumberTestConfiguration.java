package eu.deltasource.training.library;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class CucumberTestConfiguration {

    @Bean
    @Primary
    public RestTemplate mockedRestTemplate() {
        return Mockito.mock(RestTemplate.class);
    }
}
