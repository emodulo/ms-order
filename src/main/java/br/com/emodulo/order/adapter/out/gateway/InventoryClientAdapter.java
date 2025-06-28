package br.com.emodulo.order.adapter.out.gateway;

import br.com.emodulo.order.adapter.out.gateway.config.InventoryProperties;
import br.com.emodulo.order.port.out.InventoryClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.Console;

@Component
@RequiredArgsConstructor
public class InventoryClientAdapter implements InventoryClientPort {

    private final WebClient webClient;
    private final InventoryProperties properties;

    @Override
    public void decreaseStock(String productId, int quantity) {

        System.out.println(webClient);
        webClient.patch()
                .uri(properties.getSellEndpoint(), productId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}