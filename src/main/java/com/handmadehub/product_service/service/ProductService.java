package com.handmadehub.product_service.service;

import com.handmadehub.product_service.dto.*;
import com.handmadehub.product_service.entity.Product;
import com.handmadehub.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // GET /api/products
    public ProductPageResponse getProducts(
            String category, String search,
            BigDecimal minPrice, BigDecimal maxPrice,
            String sort, int page, int limit, Boolean featured) {

        Product.Category categoryEnum = null;
        if (category != null) {
            try {
                categoryEnum = Product.Category.valueOf(category.toUpperCase());
            } catch (Exception ignored) {}
        }

        Sort sortOption = switch (sort != null ? sort : "") {
            case "price-low" -> Sort.by("price").ascending();
            case "price-high" -> Sort.by("price").descending();
            default -> Sort.by("createdAt").descending();
        };

        Pageable pageable = PageRequest.of(page - 1, limit, sortOption);

        Page<Product> result = productRepository.findWithFilters(
                categoryEnum, featured, minPrice, maxPrice, search, pageable);

        List<ProductResponse> products = result.getContent()
                .stream().map(this::toResponse).collect(Collectors.toList());

        return ProductPageResponse.builder()
                .success(true)
                .count(products.size())
                .total(result.getTotalElements())
                .page(page)
                .pages(result.getTotalPages())
                .data(products)
                .build();
    }

    // GET /api/products/:id
    public ProductResponse getProduct(String idOrSlug) {
        Product product;
        try {
            Long id = Long.parseLong(idOrSlug);
            product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        } catch (NumberFormatException e) {
            product = productRepository.findBySlug(idOrSlug)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }
        return toResponse(product);
    }

    // POST /api/products
    public ProductResponse createProduct(ProductRequest req) {
        Product product = Product.builder()
                .name(req.getName())
                .slug(generateSlug(req.getName()))
                .description(req.getDescription())
                .price(req.getPrice())
                .images(req.getImages())
                .category(req.getCategory())
                .stock(req.getStock())
                .featured(req.getFeatured())
                .packSize(req.getPackSize())
                .isCustomizable(req.getIsCustomizable())
                .customizationNote(req.getCustomizationNote())
                .size(req.getSize())
                .isActive(true)
                .build();

        return toResponse(productRepository.save(product));
    }

    // PUT /api/products/:id
    public ProductResponse updateProduct(Long id, ProductRequest req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setImages(req.getImages());
        product.setCategory(req.getCategory());
        product.setStock(req.getStock());
        product.setFeatured(req.getFeatured());
        product.setPackSize(req.getPackSize());
        product.setIsCustomizable(req.getIsCustomizable());
        product.setCustomizationNote(req.getCustomizationNote());
        product.setSize(req.getSize());

        return toResponse(productRepository.save(product));
    }

    // DELETE /api/products/:id (soft delete)
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setIsActive(false);
        productRepository.save(product);
    }

    // GET /api/products/categories/all
    public List<String> getCategories() {
        return productRepository.findAllActiveCategories()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    // GET /api/products/featured/all
    public List<ProductResponse> getFeaturedProducts() {
        Pageable pageable = PageRequest.of(0, 8, Sort.by("createdAt").descending());
        return productRepository.findByIsActiveTrueAndFeaturedTrue(pageable)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Helper — generate slug from name
    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    // Helper — convert Product entity to ProductResponse DTO
    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .slug(p.getSlug())
                .description(p.getDescription())
                .price(p.getPrice())
                .images(p.getImages())
                .category(p.getCategory().name())
                .stock(p.getStock())
                .isActive(p.getIsActive())
                .featured(p.getFeatured())
                .packSize(p.getPackSize())
                .isCustomizable(p.getIsCustomizable())
                .customizationNote(p.getCustomizationNote())
                .size(p.getSize() != null ? p.getSize().name() : null)
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}