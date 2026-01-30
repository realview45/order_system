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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final OrderingDetailsRepository orderingDetailsRepository;
    private final ProductRepository productRepository;
    @Autowired
    public OrderingService(OrderingRepository orderingRepository, OrderingDetailsRepository orderingDetailsRepository, ProductRepository productRepository) {
        this.orderingRepository = orderingRepository;
        this.orderingDetailsRepository = orderingDetailsRepository;
        this.productRepository = productRepository;
    }

    public void create(List<OrderingCreateDto> dtoList) {


        Ordering ordering = orderingRepository.save(OrderingCreateDto.toEntity());
        List<OrderingDetails> orderList = ordering.getOrderList();
        for (OrderingCreateDto item : dtoList) {
            System.out.println("상품ID: " + item.getProductId());
            System.out.println("수량: " + item.getProductCount());
            OrderingDetails od = orderingDetailsRepository.save(OrderingDetails.builder()
                    .product(productRepository.findById(item.getProductId()).orElseThrow(()->new EntityNotFoundException("엔티티를 찾을 수 없습니다.")))
                    .quantity(item.getProductCount())
                    .ordering(ordering).build());
            orderList.add(od);
        }
//        for(OrderingCreateDto dto : dtoList){
//            orderList.add(
//                    orderingDetailsRepository.save(OrderingDetails.builder()
//                    .product(productRepository.findById(dto.getProductId()).orElseThrow(()->new EntityNotFoundException("상품이 없습니다.")))
//                    .quantity(dto.getProductCount())
//                    .ordering(ordering)
//                    .build()));
//        }
    }

//    public List<OrderingListDto> findAll() {
//        List<Ordering> orderingList = orderingRepository.findAll();
//        return null;
//    }
}
