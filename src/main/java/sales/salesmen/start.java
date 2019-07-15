package sales.salesmen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sales.salesmen.config.AppListener;

@SpringBootApplication
public class start {

    @Bean
    AppListener appListener() {
        return new AppListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(start.class, args);
    }
}
