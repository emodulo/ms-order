package br.com.emodulo.order.adapter.in.api.mapper;

import br.com.emodulo.order.adapter.in.api.dto.*;
import br.com.emodulo.order.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDtoMapperTest {

    private final OrderDtoMapper mapper = new OrderDtoMapper();

    @Test
    void shouldMapRequestDtoToDomain() {
        OrderRequestDTO dto = new OrderRequestDTO();
        CustomerDTO customer = new CustomerDTO("1", "Carlos", "88899977766");
        AddressDTO billing = new AddressDTO("Rua A", "12", "São Paulo", "SP", "01000-000");
        AddressDTO shipping = new AddressDTO("Rua B", "99", "São Paulo", "SP", "01001-000");
        OrderItemDTO item = new OrderItemDTO("p1", "Produto 1", 2, new BigDecimal("150.00"));

        dto.setCustomer(customer);
        dto.setBillingAddress(billing);
        dto.setShippingAddress(shipping);
        dto.setItems(List.of(item));
        dto.setShippingAddressSameAsBilling(false);

        Order domain = mapper.toDomain(dto);

        assertNotNull(domain);
        assertEquals("Carlos", domain.getCustomer().getName());
        assertEquals("Rua B", domain.getShippingAddress().getStreet());
        assertEquals(1, domain.getItems().size());
    }

    @Test
    void shouldMapRequestDtoToDomainUsingBillingAsShipping() {
        OrderRequestDTO dto = new OrderRequestDTO();
        CustomerDTO customer = new CustomerDTO("1", "Joana", "11122233344");
        AddressDTO billing = new AddressDTO("Av. Brasil", "100", "Rio", "RJ", "22222-000");
        OrderItemDTO item = new OrderItemDTO("p1", "Produto", 1, new BigDecimal("99.90"));

        dto.setCustomer(customer);
        dto.setBillingAddress(billing);
        dto.setShippingAddress(null); // será ignorado
        dto.setItems(List.of(item));
        dto.setShippingAddressSameAsBilling(true);

        Order domain = mapper.toDomain(dto);

        assertEquals("Av. Brasil", domain.getShippingAddress().getStreet());
        assertEquals(domain.getBillingAddress().getZip(), domain.getShippingAddress().getZip());
    }

    @Test
    void shouldMapDomainToResponseDto() {
        Customer customer = new Customer("2", "Lucas", "33344455566");
        Address billing = new Address("Rua 1", "10", "Campinas", "SP", "13000-000");
        Address shipping = new Address("Rua 2", "20", "Campinas", "SP", "13001-000");
        List<Item> items = List.of(new Item("p2", "Teclado", 1, new BigDecimal("199.00")));

        Order domain = new Order(10L, customer, billing, shipping, items, new BigDecimal("199.00"), LocalDateTime.now());

        OrderResponseDTO dto = mapper.toResponseDTO(domain);

        assertEquals("Lucas", dto.getCustomer().getName());
        assertEquals("Teclado", dto.getItems().get(0).getName());
        assertEquals(new BigDecimal("199.00"), dto.getTotal());
    }
}
