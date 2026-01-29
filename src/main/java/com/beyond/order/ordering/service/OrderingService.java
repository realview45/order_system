package com.beyond.order.ordering.service;

import com.beyond.order.member.dtos.MemberCreateDto;
import com.beyond.order.ordering.domain.Ordering;
import com.beyond.order.ordering.dtos.Order;
import com.beyond.order.ordering.dtos.OrderingCreateDto;
import com.beyond.order.ordering.repository.OrderingRepository;
import com.beyond.order.orderingDetails.domain.OrderingDetails;
import com.beyond.order.orderingDetails.repository.OrderingDetailsRepository;
import com.beyond.order.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final OrderingDetailsRepository orderingDetailsRepository;
    private final ProductRepository productRepository;

    public OrderingService(OrderingRepository orderingRepository, OrderingDetailsRepository orderingDetailsRepository, ProductRepository productRepository) {
        this.orderingRepository = orderingRepository;
        this.orderingDetailsRepository = orderingDetailsRepository;
        this.productRepository = productRepository;
    }

    public void create(OrderingCreateDto dto) {
        Ordering ordering = orderingRepository.save(dto.toEntity());
        List<OrderingDetails> orderList = ordering.getOrderList();
        for(Order o : dto.getOrders()){
            orderList.add(
                    orderingDetailsRepository.save(OrderingDetails.builder()
                    .product(productRepository.findById(o.getProductId()).orElseThrow(()->new EntityNotFoundException("상품이 없습니다.")))
                    .quantity(o.getProductCount())
                    .ordering(ordering)
                    .build()));
        }
    }

//    public List<OrderingListDto> findAll() {
//        List<Ordering> orderingList = orderingRepository.findAll();
//        return null;
//    }
}
