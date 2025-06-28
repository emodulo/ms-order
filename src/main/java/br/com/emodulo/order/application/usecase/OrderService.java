package br.com.emodulo.order.application.usecase;

import br.com.emodulo.order.application.usecase.config.OrderProperties;
import br.com.emodulo.order.domain.model.Item;
import br.com.emodulo.order.domain.model.Order;
import br.com.emodulo.order.port.in.OrderUseCasePort;
import br.com.emodulo.order.port.out.InventoryClientPort;
import br.com.emodulo.order.port.out.OrderRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCasePort {

    private final OrderRepositoryPort repository;
    private final InventoryClientPort inventory;
    private final OrderProperties orderProperties;

    @Override
    @Transactional
    public Order create(Order order) {
        BigDecimal total = order.getItems()
                .stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
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

        if (orderProperties.isDecreaseStockEnabled()) {
            for (Item item : order.getItems()) {
                try {
                    inventory.decreaseStock(item.getProductId(), item.getQuantity());
                } catch (Exception e) {
                    throw new IllegalStateException("Falha ao dar baixa no estoque", e);
                }
            }
        }

        return repository.save(toSave);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> listAll() {
        return repository.findAll();
    }

    @Override
    public List<Order> listByExternalId(String externalId) {
        return repository.findByExternalId(externalId);
    }
}


