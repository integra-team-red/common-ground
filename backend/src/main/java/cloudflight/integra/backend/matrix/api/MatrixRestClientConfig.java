package cloudflight.integra.backend.matrix.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.awt.*;

@Configuration
public class MatrixRestClientConfig {

    @Bean
    RestClient MatrixRestClient(RestClient.Builder builder, @Value("${matrix.base-url}")  String serverUrl) {
        return builder
            .baseUrl(serverUrl)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
