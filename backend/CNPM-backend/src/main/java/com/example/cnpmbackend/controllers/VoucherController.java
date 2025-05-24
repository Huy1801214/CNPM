package com.example.cnpmbackend.controllers;

import com.example.cnpmbackend.entity.Voucher;
import com.example.cnpmbackend.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.cnpmbackend.dto.VoucherRequestDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    // Huy (add voucher) 5.1.8. Backend Spring Boot nhận request. Spring MVC định tuyến đến VoucherController.createVoucher().
    @PostMapping
    public ResponseEntity<?> createVoucher(@Valid @RequestBody VoucherRequestDTO voucherDTO) {
        // 5.1.8. Jackson deserialize JSON payload thành Voucher (backend entity).
        try {
            Voucher savedVoucher = voucherService.createVoucher(voucherDTO);
            // 5.1.12. VoucherController trả về ResponseEntity (200 OK) chứa savedVoucher,
            // được Jackson serialize thành JSON response.
            return ResponseEntity.ok(savedVoucher);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getVoucherByCode(@PathVariable String code) {
        Optional<Voucher> voucher = voucherService.getVoucherByCode(code);
        if (voucher.isPresent()) {
            return ResponseEntity.ok(voucher.get());
        } else {
            return ResponseEntity.status(404).body("Voucher không tồn tại hoặc không còn hiệu lực.");
        }
    }

    @GetMapping
    public List<Voucher> getAllVouchers() {
        return voucherService.getAllVouchers();
    }
}

