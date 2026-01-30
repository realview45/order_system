package com.beyond.order.ordering.dtos;

import com.beyond.order.ordering.domain.OrderStatus;
import com.beyond.order.ordering.domain.Ordering;
import com.beyond.order.orderingDetails.domain.OrderingDetails;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private Long productId;
    @NotBlank
    private int productCount;
    public static Ordering toEntity() {
        return Ordering.builder()
                .orderStatus(OrderStatus.ordered)
                .created_time(now())
                .build();
    }
}
