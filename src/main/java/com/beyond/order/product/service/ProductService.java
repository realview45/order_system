package com.beyond.order.product.service;
import com.beyond.order.product.domain.Product;
import com.beyond.order.product.dtos.CreateProductDto;
import com.beyond.order.product.dtos.ProductDetailDto;
import com.beyond.order.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public void create( CreateProductDto dto) {
        productRepository.save(dto.toEntity());
    }
    public ProductDetailDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new NoSuchElementException("엔티티가 없습니다."));
        return ProductDetailDto.fromEntity(product);
    }

    public List<ProductDetailDto> findAll() {
        return productRepository.findAll().stream().map(p->ProductDetailDto.fromEntity(p)).collect(Collectors.toList());
    }
}
