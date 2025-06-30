package br.com.emodulo.order.adapter.in.api.controller;

import br.com.emodulo.order.adapter.in.api.config.ApiVersion;
import br.com.emodulo.order.adapter.in.api.dto.OrderRequestDTO;
import br.com.emodulo.order.adapter.in.api.dto.OrderResponseDTO;
import br.com.emodulo.order.adapter.in.api.mapper.OrderDtoMapper;
import br.com.emodulo.order.domain.model.Order;
import br.com.emodulo.order.port.in.OrderUseCasePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Pedidos", description = "Operações com pedidos")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping(ApiVersion.V1 + "/orders")
public class OrderController {

    private final OrderUseCasePort service;
    private final OrderDtoMapper mapper;

    public OrderController(
            OrderUseCasePort service,
            OrderDtoMapper mapper
    ) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cadastrar pedido", description = "cadastrar novo pedido de compra.")
    public ResponseEntity<OrderResponseDTO> create(
            @RequestBody OrderRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Order created = service.create(mapper.toDomain(dto, jwt));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(created));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperar pedido", description = "recuperar pedido de compra por ID.")
    public ResponseEntity<?> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {

        Optional<Order> order = service.getById(id);

        if (order.isEmpty()) return ResponseEntity.notFound().build();

        if (!order.get().getCustomer().getExternalId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }
        return ResponseEntity.ok(mapper.toResponseDTO(order.get()));
    }

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "listar pedidos do cliente logado.")
    public ResponseEntity<List<OrderResponseDTO>> listAll(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(
                service.listByExternalId(jwt.getSubject())
                        .stream()
                        .map(mapper::toResponseDTO)
                        .toList()
        );
    }
}
