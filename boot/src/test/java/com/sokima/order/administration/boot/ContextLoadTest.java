package com.sokima.order.administration.boot;

import com.sokima.order.administration.boot.test.PostgresContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ContextLoadTest implements PostgresContainer {

  @Test
  void contextLoads() {
  }

}
