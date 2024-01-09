package eu.deltasource.training.library;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"eu/deltasource/training/library", "cucumber.api.spring"}
)
public class CucumberIT {
}
