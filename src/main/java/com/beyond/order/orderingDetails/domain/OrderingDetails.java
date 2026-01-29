package com.beyond.order.orderingDetails.domain;

import com.beyond.order.ordering.domain.Ordering;
import com.beyond.order.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class OrderingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    private Ordering ordering;
    @ManyToOne
    private Product product;
    @Builder.Default
    private LocalDateTime created_time=now();
}
