package com.beyond.order.ordering.domain;


import com.beyond.order.member.domain.Member;
import com.beyond.order.orderingDetails.domain.OrderingDetails;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Member member;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ordered;
    private LocalDateTime created_time;

    @OneToMany(mappedBy = "ordering", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    List<OrderingDetails> orderList = new ArrayList<>();
}
