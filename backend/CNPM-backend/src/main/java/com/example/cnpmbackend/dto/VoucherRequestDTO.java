package com.example.cnpmbackend.dto; // Hoặc một package phù hợp

import com.example.cnpmbackend.enums.DiscountType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoucherRequestDTO {

    @NotBlank(message = "Mã voucher không được để trống")
    @Size(min = 3, max = 20, message = "Mã voucher phải từ 3 đến 20 ký tự")
    private String code;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String description;

    @NotNull(message = "Mức giảm giá không được để trống")
    @Positive(message = "Mức giảm giá phải là số dương")
    private Double discount;

    @NotNull(message = "Loại giảm giá không được để trống")
    private DiscountType discountType;

    @NotNull(message = "Số lần sử dụng tối đa không được để trống")
    @Min(value = 1, message = "Số lần sử dụng tối đa phải ít nhất là 1")
    private Integer maxUses = 1;

    @NotNull(message = "Ngày bắt đầu hiệu lực không được để trống")
    @FutureOrPresent(message = "Ngày bắt đầu hiệu lực phải là hiện tại hoặc tương lai")
    private LocalDateTime validFrom;

    @NotNull(message = "Ngày kết thúc hiệu lực không được để trống")
    @Future(message = "Ngày kết thúc hiệu lực phải là tương lai")
    private LocalDateTime validTo;

    private Boolean isActive;
}