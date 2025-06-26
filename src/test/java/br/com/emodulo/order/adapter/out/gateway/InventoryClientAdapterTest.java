package br.com.emodulo.order.adapter.out.gateway;

import br.com.emodulo.order.adapter.out.gateway.config.InventoryProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class InventoryClientAdapterTest {

    private InventoryClientAdapter adapter;
    private WebClient webClient;
    private InventoryProperties properties;

    @BeforeEach
    void setUp() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
        this.adapter = new InventoryClientAdapter(webClient, properties);
    }

    @Test
    void shouldNotThrowErrorIfVehicleServiceFails() {
        StepVerifier.create(Mono.fromRunnable(() ->
                adapter.decreaseStock("invalid-id", 10 )
        )).expectComplete().verify();
    }
}
