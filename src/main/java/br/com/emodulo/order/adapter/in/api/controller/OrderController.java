package br.com.emodulo.order.adapter.in.api.controller;

import br.com.emodulo.order.adapter.in.api.dto.OrderRequestDTO;
import br.com.emodulo.order.adapter.in.api.dto.OrderResponseDTO;
import br.com.emodulo.order.adapter.in.api.mapper.OrderDtoMapper;
import br.com.emodulo.order.domain.model.Order;
import br.com.emodulo.order.port.in.OrderUseCasePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operações com pedidos")
public class OrderController {

    private final OrderUseCasePort service;
    private final OrderDtoMapper mapper;

    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cria novo pedido de compra.")
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO dto) {
        Order created = service.create(mapper.toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(created));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperar pedido", description = "Recupera pedido de compra por ID.")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponseDTO(service.getById(id)));
    }

    @GetMapping
    @Operation(summary = "listar pedidos", description = "Lista pedidos de compra.")
    public ResponseEntity<List<OrderResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList());
    }
}
