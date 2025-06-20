package br.com.emodulo.order.application.usecase;

import br.com.emodulo.order.domain.model.Order;
import br.com.emodulo.order.port.in.OrderUseCasePort;
import br.com.emodulo.order.port.out.InventoryClientPort;
import br.com.emodulo.order.port.out.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCasePort {

    private final OrderRepositoryPort repository;
    private final InventoryClientPort inventoryClient;

    @Override
    public Order create(Order order) {
        // calcula total
        BigDecimal total = order.getItems().stream()
                .map(i -> i.unitPrice().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order toSave = new Order(
                null,
                order.getCustomer(),
                order.getBillingAddress(),
                order.getShippingAddress(),
                order.getItems(),
                total,
                LocalDateTime.now()
        );

        Order saved = repository.save(toSave);

        // baixa estoque
        order.getItems().forEach(item ->
                inventoryClient.decreaseStock(item.productId(), item.quantity()));

        return saved;
    }

    @Override
    public Order getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    @Override
    public List<Order> listAll() {
        return repository.findAll();
    }
}

