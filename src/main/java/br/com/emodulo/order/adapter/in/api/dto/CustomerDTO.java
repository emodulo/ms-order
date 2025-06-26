package br.com.emodulo.order.adapter.in.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotBlank(message = "Id do cliente não pode ser vazio")
    private String id;
    
    @NotBlank(message = "Nome do cliente não pode ser vazio")
    private String name;

    @NotBlank(message = "Documento do cliente não pode ser vazio")
    private String document;

    @NotBlank(message = "Email do cliente não pode ser vazio")
    private String email;
}

