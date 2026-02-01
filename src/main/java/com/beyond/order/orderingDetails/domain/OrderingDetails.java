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
    private Long quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordering_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Ordering ordering;
    @ManyToOne
    private Product product;
    @Builder.Default
    private LocalDateTime created_time=now();
}
