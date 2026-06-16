package com.handmadehub.product_service.dto;
import com.handmadehub.product_service.entity.Product;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
@Data
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(max = 200)
    private String name;
    @NotBlank(message = "Description is required")
    @Size(max = 2000)
    private String description;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private BigDecimal price;
    private List<String> images;
    @NotNull(message = "Category is required")
    private Product.Category category;
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;
    private Boolean featured = false;
    private Integer packSize = 1;
    private Boolean isCustomizable = false;
    private String customizationNote;
    private Product.Size size = Product.Size.NA;
}