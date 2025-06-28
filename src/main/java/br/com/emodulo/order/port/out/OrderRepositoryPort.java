package br.com.emodulo.order.port.out;

import br.com.emodulo.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    List<Order> findByExternalId(String externalId);
}
