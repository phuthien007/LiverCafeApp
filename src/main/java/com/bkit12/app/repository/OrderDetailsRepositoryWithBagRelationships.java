package com.bkit12.app.repository;

import com.bkit12.app.domain.OrderDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface OrderDetailsRepositoryWithBagRelationships {
    Optional<OrderDetails> fetchBagRelationships(Optional<OrderDetails> orderDetails);

    List<OrderDetails> fetchBagRelationships(List<OrderDetails> orderDetails);

    Page<OrderDetails> fetchBagRelationships(Page<OrderDetails> orderDetails);
}
