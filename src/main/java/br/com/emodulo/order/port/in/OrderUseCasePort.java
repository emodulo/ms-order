package br.com.emodulo.order.port.in;

import br.com.emodulo.order.domain.model.Order;

import java.util.List;

public interface OrderUseCasePort {
    Order create(Order order);
    Order getById(Long id);
    List<Order> listAll();
}
