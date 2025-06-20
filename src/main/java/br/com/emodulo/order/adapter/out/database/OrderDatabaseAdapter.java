package br.com.emodulo.order.adapter.out.database;

import br.com.emodulo.order.adapter.out.database.mapper.OrderEntityMapper;
import br.com.emodulo.order.adapter.out.database.repository.OrderJpaRepository;
import br.com.emodulo.order.domain.model.Order;
import br.com.emodulo.order.port.out.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderDatabaseAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository repository;
    private final OrderEntityMapper mapper;

    @Override
    public Order save(Order order) {
        return mapper.toDomain(repository.save(mapper.toEntity(order)));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
