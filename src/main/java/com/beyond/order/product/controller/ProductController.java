package com.beyond.order.product.controller;

import com.beyond.order.product.dtos.CreateProductDto;
import com.beyond.order.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute CreateProductDto dto){
        productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
}
