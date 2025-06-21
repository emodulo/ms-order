package br.com.emodulo.order.adapter.out.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

public class WebClientConfigTest {

    @Test
    void shouldCreateWebClientBean() {
        WebClientConfig config = new WebClientConfig();
        WebClient client = config.webClient();

        assertNotNull(client);
        assertEquals(WebClient.class, client.getClass());
    }
}