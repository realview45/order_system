package com.beyond.order.product.service;
import com.beyond.order.member.domain.Member;
import com.beyond.order.member.repository.MemberRepository;
import com.beyond.order.product.domain.Product;
import com.beyond.order.product.dtos.CreateProductDto;
import com.beyond.order.product.dtos.ProductDetailDto;
import com.beyond.order.product.dtos.ProductListDto;
import com.beyond.order.product.dtos.ProductSearchDto;
import com.beyond.order.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    public ProductService(ProductRepository productRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }
    public Long create( CreateProductDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("엔티티가 없습니다."));
        Product product = productRepository.save(dto.toEntity(member));
        return product.getId();
    }
    public ProductDetailDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("엔티티가 없습니다."));
        return ProductDetailDto.fromEntity(product);
    }

    public Page<ProductListDto> findAll(Pageable pageable, ProductSearchDto searchDto) {
        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if(searchDto.getProductName()!= null){
                    predicateList.add(criteriaBuilder.equal(root.get("name"), searchDto.getProductName()));
                }
                if(searchDto.getCategory()!=null) {
                    predicateList.add(criteriaBuilder.equal(root.get("category"), searchDto.getCategory()));
                }
                Predicate[] predicateArr = new Predicate[predicateList.size()];
                for(int i=0;i<predicateArr.length;i++){
                    predicateArr[i] = predicateList.get(i);
                }
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
            }
        };
        Page<Product> productList = productRepository.findAll(specification, pageable);
        return productList.map(p-> ProductListDto.fromEntity(p));
    }
}
