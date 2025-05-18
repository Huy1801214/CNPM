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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

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