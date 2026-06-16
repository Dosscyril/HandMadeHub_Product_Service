package com.handmadehub.product_service.dto;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private BigDecimal price;
    private List<String> images;
    private String category;
    private Integer stock;
    private Boolean isActive;
    private Boolean featured;
    private Integer packSize;
    private Boolean isCustomizable;
    private String customizationNote;
    private String size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}