package br.com.emodulo.order.adapter.out.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "inventory")
@Getter
@Setter
public class InventoryProperties {
    private String baseUrl;
    private String sellEndpoint;
}
