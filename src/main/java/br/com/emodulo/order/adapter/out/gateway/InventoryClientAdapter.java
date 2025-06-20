package br.com.emodulo.order.adapter.out.gateway;

import br.com.emodulo.order.port.out.InventoryClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class InventoryClientAdapter implements InventoryClientPort {

    private final WebClient webClient;

    @Override
    public void decreaseStock(String productId, int quantity) {

        webClient.patch()
                .uri("/vehicles/{id}/sell", productId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}