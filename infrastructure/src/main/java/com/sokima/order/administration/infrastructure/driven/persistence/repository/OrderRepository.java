package com.sokima.order.administration.infrastructure.driven.persistence.repository;

import com.sokima.order.administration.infrastructure.driven.persistence.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

}
