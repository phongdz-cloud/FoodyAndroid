package hcmute.edu.vn.foody_10.Model;

public class OrderModel {
    private Integer id;
    private byte[] photoFood;
    private Integer count;
    private String foodName;
    private String foodDescription;
    private Float price;
    private Integer productId;
    private Integer userId;

    public OrderModel() {
    }

    public OrderModel(Integer id, byte[] photoFood, Integer count, String foodName, String foodDescription, Float price, Integer productId, Integer userId) {
        this.id = id;
        this.photoFood = photoFood;
        this.count = count;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.price = price;
        this.productId = productId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getPhotoFood() {
        return photoFood;
    }

    public void setPhotoFood(byte[] photoFood) {
        this.photoFood = photoFood;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
