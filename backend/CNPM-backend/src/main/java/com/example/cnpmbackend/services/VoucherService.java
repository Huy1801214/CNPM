package com.example.cnpmbackend.services;

import com.example.cnpmbackend.entity.Voucher;
import com.example.cnpmbackend.responsitory.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    // Huy (add voucher) 5.1.9. Backend thực hiện Bean Validation (nếu có @Valid) và
    // sau đó VoucherService thực hiện các kiểm tra logic nghiệp vụ (mã trùng, ngày hợp lệ).
    public Voucher createVoucher(Voucher voucher) {
        if (voucherRepository.findByCode(voucher.getCode()).isPresent()) {
            throw new IllegalArgumentException("Voucher với mã " + voucher.getCode() + " đã tồn tại.");
        }
        if (voucher.getValidFrom().isAfter(voucher.getValidTo())) {
            throw new IllegalArgumentException("Ngày bắt đầu không được sau ngày kết thúc.");
        }
        if (voucher.getMaxUses() < 1) {
            throw new IllegalArgumentException("Số lần sử dụng tối đa phải lớn hơn hoặc bằng 1.");
        }
        // 5.1.10. Nếu tất cả kiểm tra hợp lệ, VoucherService gọi VoucherRepository.save(voucher).
        // 5.1.11. Hibernate (ORM) tạo và thực thi câu lệnh SQL INSERT để lưu Voucher entity vào database MySQL.
        return voucherRepository.save(voucher);
    }

    public Optional<Voucher> getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    private boolean isVoucherValid(Voucher voucher) {
        LocalDateTime now = LocalDateTime.now();
        return voucher.isActive() &&
                now.isAfter(voucher.getValidFrom()) &&
                now.isBefore(voucher.getValidTo()) &&
                voucher.getUsedCount() < voucher.getMaxUses();
    }
}
