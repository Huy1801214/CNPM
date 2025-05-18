package com.example.cnpmfrontend.model;

import com.google.gson.annotations.SerializedName;

// Lớp này phải khớp với cấu trúc JSON mà backend trả về cho một Voucher.
// Các annotation @SerializedName dùng nếu tên trường Java khác tên trong JSON.
public class Voucher {

    @SerializedName("id")
    private int id;

    @SerializedName("code")
    private String code;

    @SerializedName("description")
    private String description;

    @SerializedName("discount")
    private Double discount;

    // Giả sử backend trả về DiscountType dưới dạng String ("percentage", "fixed_amount")
    // Nếu backend trả về Enum, bạn cần định nghĩa Enum tương tự ở đây
    // hoặc dùng TypeAdapter cho Gson. Để đơn giản, ta dùng String.
    @SerializedName("discountType")
    private String discountType;

    @SerializedName("maxUses")
    private Integer maxUses;

    @SerializedName("usedCount")
    private Integer usedCount;

    // Backend sẽ trả về LocalDateTime dưới dạng chuỗi ISO 8601 (ví dụ: "2024-05-18T10:30:00")
    // Gson có thể parse trực tiếp thành String.
    @SerializedName("validFrom")
    private String validFrom; // Sẽ là chuỗi ISO 8601

    @SerializedName("validTo")
    private String validTo;   // Sẽ là chuỗi ISO 8601

    @SerializedName("active") // Kiểm tra backend trả về "active" hay "isActive"
    private boolean isActive;

    // Constructor (tùy chọn cho Gson)
    public Voucher() {
    }

    // Getters
    public int getId() { return id; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public Double getDiscount() { return discount; }
    public String getDiscountType() { return discountType; }
    public Integer getMaxUses() { return maxUses; }
    public Integer getUsedCount() { return usedCount; }
    public String getValidFrom() { return validFrom; }
    public String getValidTo() { return validTo; }
    public boolean isActive() { return isActive; } // Getter cho boolean thường là isFieldName()

    // Setters (tùy chọn cho Gson)
    public void setId(int id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; }
    public void setDiscount(Double discount) { this.discount = discount; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }
    public void setMaxUses(Integer maxUses) { this.maxUses = maxUses; }
    public void setUsedCount(Integer usedCount) { this.usedCount = usedCount; }
    public void setValidFrom(String validFrom) { this.validFrom = validFrom; }
    public void setValidTo(String validTo) { this.validTo = validTo; }
    public void setActive(boolean active) { isActive = active; }
}