package binarek.robio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = JooqAutoConfiguration.class) // replaced with custom JOOQ configuration
public class RobioServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobioServerApplication.class, args);
    }

}
