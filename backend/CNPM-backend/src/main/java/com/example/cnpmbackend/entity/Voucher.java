package com.example.cnpmbackend.entity;

import com.example.cnpmbackend.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, unique = true, nullable = false)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", length = 10)
    private DiscountType discountType;

    @Column(name = "max_uses", columnDefinition = "INT DEFAULT 1")
    private Integer maxUses = 1;

    @Column(name = "used_count", columnDefinition = "INT DEFAULT 0")
    private Integer usedCount = 0;

    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;
}