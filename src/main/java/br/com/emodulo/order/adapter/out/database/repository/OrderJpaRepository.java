package br.com.emodulo.order.adapter.out.database.repository;

import br.com.emodulo.order.adapter.out.database.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}