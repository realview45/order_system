package com.beyond.order.ordering.dtos;

import com.beyond.order.ordering.domain.OrderStatus;
import com.beyond.order.ordering.domain.Ordering;
import com.beyond.order.orderingDetails.domain.OrderingDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.time.LocalDateTime.now;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderingCreateDto {
    private List<Order> orders;

    public Ordering toEntity() {
        return Ordering.builder()
                .orderStatus(OrderStatus.ordered)
                .created_time(now())
                .build();
    }

}
