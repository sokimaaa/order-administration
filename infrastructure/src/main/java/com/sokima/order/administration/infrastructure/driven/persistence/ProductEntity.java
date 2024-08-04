package com.sokima.order.administration.infrastructure.driven.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

  @Id
  @Column(name = "product_id")
  private String productId;

  @Column(name = "amount", nullable = false)
  private Float amount;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderEntity order;
}
