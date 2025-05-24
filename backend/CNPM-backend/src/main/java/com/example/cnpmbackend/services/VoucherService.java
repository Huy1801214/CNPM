package com.example.cnpmbackend.services;

import com.example.cnpmbackend.dto.VoucherRequestDTO;
import com.example.cnpmbackend.entity.Voucher;
import com.example.cnpmbackend.responsitory.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    // Huy (add voucher) 5.1.9. Backend thực hiện Bean Validation (nếu có @Valid) và
    // sau đó VoucherService thực hiện các kiểm tra logic nghiệp vụ (mã trùng, ngày hợp lệ).
    public Voucher createVoucher(VoucherRequestDTO voucherDTO) { // Nhận DTO
        if (voucherRepository.findByCode(voucherDTO.getCode()).isPresent()) {
            throw new IllegalArgumentException("Voucher với mã " + voucherDTO.getCode() + " đã tồn tại.");
        }
        if (voucherDTO.getValidFrom().isAfter(voucherDTO.getValidTo())) {
            throw new IllegalArgumentException("Ngày bắt đầu không được sau ngày kết thúc.");
        }
        if (voucherDTO.getMaxUses() < 1) {
            throw new IllegalArgumentException("Số lần sử dụng tối đa phải lớn hơn hoặc bằng 1.");
        }

        Voucher voucher = new Voucher();
        // Map dữ liệu từ DTO sang Entity
        // QUAN TRỌNG: KHÔNG map/set ID ở đây
        voucher.setCode(voucherDTO.getCode());
        voucher.setDescription(voucherDTO.getDescription());
        voucher.setDiscount(voucherDTO.getDiscount());
        voucher.setDiscountType(voucherDTO.getDiscountType());
        voucher.setMaxUses(voucherDTO.getMaxUses());
        voucher.setValidFrom(voucherDTO.getValidFrom());
        voucher.setValidTo(voucherDTO.getValidTo());

        // Xử lý isActive:
        if (voucherDTO.getIsActive() != null) {
            voucher.setActive(voucherDTO.getIsActive());
        } else {
            voucher.setActive(true); // Giá trị mặc định nếu client không gửi
        }
        // usedCount sẽ có giá trị mặc định là 0 từ entity

        return voucherRepository.save(voucher);
    }

    public Optional<Voucher> getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
    
}
