package br.com.emodulo.order.adapter.in.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String street;
    private String number;
    private String city;
    private String state;
    private String zip;
}
