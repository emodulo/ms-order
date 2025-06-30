package br.com.emodulo.order.adapter.in.api.mapper;

import br.com.emodulo.order.adapter.in.api.dto.*;
import br.com.emodulo.order.domain.model.*;
import br.com.emodulo.order.factory.OrderTestDataFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDtoMapperTest {

    private final OrderDtoMapper mapper = new OrderDtoMapper();

    @Test
    void shouldMapRequestDtoToDomain() {
        OrderRequestDTO dto = OrderTestDataFactory.buildOrderRequestDTO();

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("user-sub-123");

        Order domain = mapper.toDomain(dto, jwt);

        assertNotNull(domain);
        assertEquals("Carlos", domain.getCustomer().getName());
        assertEquals("Rua B", domain.getShippingAddress().getStreet());
        assertEquals(1, domain.getItems().size());
    }

    @Test
    void shouldMapRequestDtoToDomainUsingBillingAsShipping() {
        OrderRequestDTO dto = OrderTestDataFactory.buildOrderRequestDTO();

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("user-sub-123");

        Order domain = mapper.toDomain(dto, jwt);

        assertEquals("Rua B", domain.getShippingAddress().getStreet());
        assertEquals(domain.getBillingAddress().getZip(), domain.getShippingAddress().getZip());
    }

    @Test
    void shouldMapDomainToResponseDto() {
        Order domain = OrderTestDataFactory.buildOrderDomain();

        OrderResponseDTO dto = mapper.toResponseDTO(domain);

        assertEquals("Lucas", dto.getCustomer().getName());
        assertEquals("Teclado", dto.getItems().get(0).getName());
        assertEquals(new BigDecimal("199.00"), dto.getTotal());
    }
}
