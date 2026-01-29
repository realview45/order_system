package com.beyond.order.product.service;
import com.beyond.order.product.dtos.CreateProductDto;
import com.beyond.order.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(CreateProductDto dto) {
        productRepository.save(dto.toEntity());
    }
}
