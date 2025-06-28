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
public class AddressDTO {

    @NotBlank(message = "street do endereço não pode ser vazio")
    private String street;

    @NotBlank(message = "number do endereço não pode ser vazio")
    private String number;

    @NotBlank(message = "City do endereço não pode ser vazio")
    private String city;

    @NotBlank(message = "State do endereço não pode ser vazio")
    private String state;

    @NotBlank(message = "Zip do endereço não pode ser vazio")
    private String zip;
}
