package com.beyond.order.member.domain;

import com.beyond.order.member.dtos.MemberDetailDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=50,unique=true,nullable=false)
    private String email;
    private String password;
    private String name;

    @Builder.Default
    private Role role=Role.USER;
    private LocalDateTime created_time;

}
