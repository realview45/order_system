package com.beyond.order.member.domain;

import com.beyond.order.member.dtos.MemberDetailDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String email;
    private String password;
    private String name;

    @Builder.Default
    private Role role=Role.USER;
    private LocalDateTime created_time;

}
