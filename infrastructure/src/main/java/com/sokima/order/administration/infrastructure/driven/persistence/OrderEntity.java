package com.sokima.order.administration.infrastructure.driven.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

  @Id
  @Column(name = "order_id")
  private String orderId;

  @Column(name = "account_id", nullable = false)
  private String accountId;

  @Embedded
  @Column(name = "status", nullable = false)
  private StatusEmb status;

  @Embedded
  @Column(name = "delivery")
  private DeliveryEmb delivery;

  @OneToMany(mappedBy = "order")
  private List<ProductEntity> products;
}
