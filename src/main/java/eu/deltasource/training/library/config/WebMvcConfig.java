package eu.deltasource.training.library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${front.end.mapping.get.books}")
    private String getBooksMapping;
    @Value("${front.end.mapping.post.book}")
    private String addBookMapping;
    @Value("${front.end.origin}")
    private String frontEndOrigin;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(getBooksMapping)
                .allowedOrigins(frontEndOrigin)
                .allowedMethods("GET");

        registry.addMapping(addBookMapping)
                .allowedOrigins(frontEndOrigin)
                .allowedMethods("POST");
    }
}
