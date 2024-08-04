package com.sokima.order.administration.infrastructure.driven.persistence;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class DeliveryEmb {

  private String shippingAddress;

  private String packageDimension;
}
