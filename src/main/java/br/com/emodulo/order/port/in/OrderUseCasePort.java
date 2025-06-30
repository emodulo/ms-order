package br.com.emodulo.order.port.in;

import br.com.emodulo.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderUseCasePort {
    Order create(Order order);
    Optional<Order> getById(Long id);
    List<Order> listAll();
    List<Order> listByExternalId(String externalId);
}
