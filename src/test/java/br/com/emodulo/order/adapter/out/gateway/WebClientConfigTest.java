package br.com.emodulo.order.adapter.out.gateway;

import br.com.emodulo.order.adapter.out.gateway.config.InventoryProperties;
import br.com.emodulo.order.adapter.out.gateway.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class WebClientConfigTest {

    private final InventoryProperties properties;

    @Test
    void shouldCreateWebClientBean() {
        WebClientConfig config = new WebClientConfig(properties);
        WebClient client = config.webClient();

        assertNotNull(client);
        assertEquals(WebClient.class, client.getClass());
    }
}