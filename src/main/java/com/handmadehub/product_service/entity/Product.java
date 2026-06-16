package com.handmadehub.product_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "product_images",
        joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Boolean featured = false;

    @Column(nullable = false)
    private Integer packSize = 1;

    @Column(nullable = false)
    private Boolean isCustomizable = false;

    private String customizationNote;

    @Enumerated(EnumType.STRING)
    private Size size = Size.NA;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Category {
        BOUQUETS, FRAMES, STRING_ART, CLAY_ITEMS,
        ACCESSORIES, PHONE_CASES, CUPS, MINI_ITEMS,
        CARDS, HAMPERS
    }

    public enum Size {
        SMALL, MEDIUM, LARGE, A5, STANDARD, NA
    }
}