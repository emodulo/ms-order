package br.com.emodulo.order.adapter.in.api.controller;

import br.com.emodulo.order.adapter.in.api.dto.OrderRequestDTO;
import br.com.emodulo.order.adapter.in.api.dto.OrderResponseDTO;
import br.com.emodulo.order.adapter.in.api.mapper.OrderDtoMapper;
import br.com.emodulo.order.domain.model.Order;
import br.com.emodulo.order.port.in.OrderUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCasePort service;
    private final OrderDtoMapper mapper;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO dto) {
        Order created = service.create(mapper.toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponseDTO(service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList());
    }
}
