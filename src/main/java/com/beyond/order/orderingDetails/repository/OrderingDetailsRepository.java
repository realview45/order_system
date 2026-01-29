package com.beyond.order.orderingDetails.repository;

import com.beyond.order.orderingDetails.domain.OrderingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderingDetailsRepository extends JpaRepository <OrderingDetails, Long>{
}
