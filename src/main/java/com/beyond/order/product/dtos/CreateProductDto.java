package com.beyond.order.product.dtos;

import com.beyond.order.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    private String name;
    private Long price;
    private String category;
    private Long stockQuantity;
    private MultipartFile productImage;

    public Product toEntity() {
        return Product.builder()
                .name(name).price(price).category(category).stockQuantity(stockQuantity)
                .image_path("path").build();
    }
}
