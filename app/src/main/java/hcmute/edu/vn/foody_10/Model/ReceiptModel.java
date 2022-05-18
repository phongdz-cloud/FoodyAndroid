package hcmute.edu.vn.foody_10.Model;

import androidx.annotation.NonNull;

public class ReceiptModel {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer totalCount;
    private Float totalPrice;
    private String code;

    public ReceiptModel() {
    }

    public ReceiptModel(Integer userId, Integer productId, Integer totalCount, Float totalPrice, String code) {
        this.userId = userId;
        this.productId = productId;
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReceiptModel{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", totalCount=" + totalCount +
                ", totalPrice=" + totalPrice +
                ", code='" + code + '\'' +
                '}';
    }
}
