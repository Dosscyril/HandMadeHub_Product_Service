package com.handmadehub.product_service.dto;
import lombok.*;
import java.util.List;
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ProductPageResponse {
    private boolean success;
    private int count;
    private long total;
    private int page;
    private int pages;
    private List<ProductResponse> data;
}