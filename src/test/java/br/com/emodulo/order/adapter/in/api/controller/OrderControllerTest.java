package br.com.emodulo.order.adapter.in.api.controller;

import br.com.emodulo.order.adapter.in.api.dto.*;
import br.com.emodulo.order.adapter.in.api.mapper.OrderDtoMapper;
import br.com.emodulo.order.application.usecase.OrderService;
import br.com.emodulo.order.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest {

    private MockMvc mockMvc;
    private OrderService service;
    private OrderDtoMapper mapper;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        service = Mockito.mock(OrderService.class);
        mapper = new OrderDtoMapper();
        objectMapper = new ObjectMapper();
        OrderController controller = new OrderController(service, mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        OrderRequestDTO requestDTO = new OrderRequestDTO(
                new CustomerDTO("123", "João", "12345678900"),
                new AddressDTO("Rua A", "123", "Cidade", "SP", "00000-000"),
                new AddressDTO("Rua A", "123", "Cidade", "SP", "00000-000"),
                List.of(new OrderItemDTO("1", "Produto", 2, new BigDecimal("100.00"))),
                true
        );

        Order order = mapper.toDomain(requestDTO);
        Order createdOrder = new Order(1L, order.getCustomer(), order.getBillingAddress(), order.getShippingAddress(),
                order.getItems(), new BigDecimal("200.00"), LocalDateTime.now());

        when(service.create(Mockito.any(Order.class))).thenReturn(createdOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldGetOrderById() throws Exception {
        Order order = new Order(1L,
                new Customer("123", "João", "12345678900"),
                new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                List.of(new Item("1", "Produto", 1, new BigDecimal("100.00"))),
                new BigDecimal("100.00"),
                LocalDateTime.now());

        when(service.getById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customer.name").value("João"));
    }

    @Test
    void shouldListAllOrders() throws Exception {
        List<Order> orders = List.of(
                new Order(1L,
                        new Customer("123", "João", "12345678900"),
                        new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                        new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                        List.of(new Item("1", "Produto", 1, new BigDecimal("100.00"))),
                        new BigDecimal("100.00"),
                        LocalDateTime.now())
        );

        when(service.listAll()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}