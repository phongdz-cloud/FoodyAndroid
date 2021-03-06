package hcmute.edu.vn.foody_10.Model;

import java.io.Serializable;

public class FoodModel implements Serializable {
    private Integer id;
    private byte[] photoFood;
    private String foodName;
    private String foodDescription;
    private Float price;
    private Integer userId;
    private Integer categoryId;

    public FoodModel() {
    }

    public FoodModel(Integer id, byte[] photoFood, String foodName, String foodDescription, Float price, Integer userId, Integer categoryId) {
        this.id = id;
        this.photoFood = photoFood;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.price = price;
        this.userId = userId;
        this.categoryId = categoryId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
