package hcmute.edu.vn.foody_10.orders;

public class OrderModel {
    private Integer id;
    private Integer photoFood;
    private Integer count;
    private String foodName;
    private String foodDescription;
    private float price;

    public OrderModel(Integer id, Integer photoFood, Integer count, String foodName, String foodDescription, float price) {
        this.id = id;
        this.photoFood = photoFood;
        this.count = count;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhotoFood() {
        return photoFood;
    }

    public void setPhotoFood(Integer photoFood) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
