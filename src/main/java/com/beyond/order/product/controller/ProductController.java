package com.beyond.order.product.controller;

import com.beyond.order.member.dtos.MemberDetailDto;
import com.beyond.order.product.domain.Product;
import com.beyond.order.product.dtos.CreateProductDto;
import com.beyond.order.product.dtos.ProductDetailDto;
import com.beyond.order.product.dtos.ProductListDto;
import com.beyond.order.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    //상품등록
    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute CreateProductDto dto){
        productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
    //상품상세조회
    @GetMapping("/detail/{id}")
    public ProductDetailDto findById(@PathVariable Long id){
        return productService.findById(id);
    }
    //상품상세조회(검색)
    @GetMapping("/list")
    public Page<ProductListDto> findAll(Pageable pageable){
        return productService.findAll(pageable);
    }
}
