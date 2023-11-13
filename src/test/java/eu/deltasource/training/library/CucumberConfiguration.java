package eu.deltasource.training.library;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@CucumberContextConfiguration
@ContextConfiguration(classes = LibraryApplication.class)
public class CucumberConfiguration {
}
