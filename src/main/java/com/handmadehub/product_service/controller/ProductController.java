package com.handmadehub.product_service.controller;

import com.handmadehub.product_service.dto.*;
import com.handmadehub.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductPageResponse> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) Boolean featured) {
        return ResponseEntity.ok(productService.getProducts(
                category, search, minPrice, maxPrice, sort, page, limit, featured));
    }

    @GetMapping("/categories/all")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(Map.of("success", true,
                "data", productService.getCategories()));
    }

    @GetMapping("/featured/all")
    public ResponseEntity<?> getFeaturedProducts() {
        List<ProductResponse> products = productService.getFeaturedProducts();
        return ResponseEntity.ok(Map.of("success", true,
                "count", products.size(), "data", products));
    }

    @GetMapping("/{idOrSlug}")
    public ResponseEntity<?> getProduct(@PathVariable String idOrSlug) {
        return ResponseEntity.ok(Map.of("success", true,
                "data", productService.getProduct(idOrSlug)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.status(201).body(Map.of("success", true,
                "data", productService.createProduct(req)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(Map.of("success", true,
                "data", productService.updateProduct(id, req)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(Map.of("success", true,
                "message", "Product deleted successfully"));
    }
}