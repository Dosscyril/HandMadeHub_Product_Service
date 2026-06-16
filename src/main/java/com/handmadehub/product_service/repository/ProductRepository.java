package com.handmadehub.product_service.repository;
import com.handmadehub.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);

    List<Product> findByIsActiveTrueAndFeaturedTrue(Pageable pageable);

    List<Product> findDistinctCategoryByIsActiveTrue();

    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.isActive = true")
    List<Product.Category> findAllActiveCategories();

    @Query("""
        SELECT p FROM Product p
        WHERE p.isActive = true
        AND (:category IS NULL OR p.category = :category)
        AND (:featured IS NULL OR p.featured = :featured)
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        AND (:search IS NULL OR
             LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
             LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')))
        """)
    Page<Product> findWithFilters(
            @Param("category") Product.Category category,
            @Param("featured") Boolean featured,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("search") String search,
            Pageable pageable
    );
}