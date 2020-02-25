package nl.tudelft.oopp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

//@ComponentScan("nl.tudelft.oopp.demo.repositories.QuoteRepository")
//@ComponentScan("nl/tudelft/oopp/demo/config/H2Config.java")
@Component
@EnableJpaRepositories
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

}
