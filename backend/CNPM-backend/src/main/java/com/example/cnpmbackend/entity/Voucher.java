package com.example.cnpmbackend.entity;

import com.example.cnpmbackend.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "voucher")

public class Voucher {
    public Voucher() {
    }

    public Voucher(Integer id, String code, String description, Double discount, DiscountType discountType, Integer maxUses, Integer usedCount, LocalDateTime validFrom, LocalDateTime validTo, boolean isActive) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discount = discount;
        this.discountType = discountType;
        this.maxUses = maxUses;
        this.usedCount = usedCount;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Integer getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(Integer maxUses) {
        this.maxUses = maxUses;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Mã voucher không được để trống")
    @Size(min = 3, max = 20, message = "Mã voucher phải từ 3 đến 20 ký tự")
    @Column(length = 20, unique = true, nullable = false)
    private String code;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Mức giảm giá không được để trống")
    @Positive(message = "Mức giảm giá phải là số dương")
    @Column(nullable = false)
    private Double discount;

    @NotNull(message = "Loại giảm giá không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", length = 10)
    private DiscountType discountType;

    @NotNull(message = "Số lần sử dụng tối đa không được để trống")
    @Min(value = 1, message = "Số lần sử dụng tối đa phải ít nhất là 1")
    @Column(name = "max_uses", columnDefinition = "INT DEFAULT 1")
    private Integer maxUses = 1;

    @Column(name = "used_count", columnDefinition = "INT DEFAULT 0")
    private Integer usedCount = 0;

    @NotNull(message = "Ngày bắt đầu hiệu lực không được để trống")
    @FutureOrPresent(message = "Ngày bắt đầu hiệu lực phải là hiện tại hoặc tương lai")
    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @NotNull(message = "Ngày kết thúc hiệu lực không được để trống")
    @Future(message = "Ngày kết thúc hiệu lực phải là tương lai")
    @Column(name = "valid_to")
    private LocalDateTime validTo;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;
}