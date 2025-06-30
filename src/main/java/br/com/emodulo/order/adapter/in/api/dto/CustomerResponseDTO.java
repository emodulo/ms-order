package br.com.emodulo.order.adapter.in.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO extends CustomerDTO {

    private String externalId;

    public CustomerResponseDTO(
            String id,
            String name,
            String document,
            String email,
            String externalId) {
        super(id, name, document, email);
        this.externalId = externalId;
    }

    public CustomerResponseDTO(String externalId) {
        this.externalId = externalId;
    }
}

