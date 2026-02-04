package com.beyond.order.common.service;

import com.beyond.order.common.dtos.RabbitMqStockDto;
import com.beyond.order.product.domain.Product;
import com.beyond.order.product.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RabbitMqStockService {
    private final RabbitTemplate rabbitTemplate;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public RabbitMqStockService(RabbitTemplate rabbitTemplate, ProductRepository productRepository, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public void publish(Long productId, int productCount){
        RabbitMqStockDto dto = RabbitMqStockDto.builder()
                .productId(productId)
                .productCount(productCount)
                .build();
        System.out.println("publish");
        rabbitTemplate.convertAndSend("stockQueue",dto);
    }
//  RabbitListener : rabbitmq에 특정 큐에 대해 subscribe하는 어노테이션
//  RabbitListener는 단일스레드로 메시지를 처리하므로, 동시성이슈발생X 다만, 멀티서버환경에서는 문제발생할 수 있음.
    @RabbitListener(queues = "stockQueue")//어노테이션을 붙이면 바라본다 redis 객체4개만든것과 비교구리
    @Transactional
    public void subscribe(Message message) throws JsonProcessingException {
        String messageBody = new String(message.getBody());
        System.out.println("message : " + messageBody);
        RabbitMqStockDto dto = objectMapper.readValue(messageBody, RabbitMqStockDto.class);
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(()->new EntityNotFoundException("엔티티가없습니다."));
        product.updateStockQuantity(dto.getProductCount());
    }
}
