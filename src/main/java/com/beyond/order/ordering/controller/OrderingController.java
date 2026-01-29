package com.beyond.order.ordering.controller;

import com.beyond.order.member.dtos.MemberCreateDto;
import com.beyond.order.ordering.domain.Ordering;
import com.beyond.order.ordering.dtos.OrderingCreateDto;
import com.beyond.order.ordering.service.OrderingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordering")
public class OrderingController {
    private final OrderingService orderingService;
    public OrderingController(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid OrderingCreateDto dto){
        orderingService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
//    @GetMapping("/list")
//    public ResponseEntity<?> findAll(){
//        orderingService.findAll();
//        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
//    }
}
