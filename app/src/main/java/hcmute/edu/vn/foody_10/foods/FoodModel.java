package hcmute.edu.vn.foody_10.foods;

public class FoodModel {
    private Integer id;
    private Integer photoFood;
    private String foodName;
    private String foodDescription;
    private float price;

    public FoodModel() {
    }

    public FoodModel(Integer id, Integer photoFood, String foodName, String foodDescription, float price) {
        this.id = id;
        this.photoFood = photoFood;
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

    public int getPhotoFood() {
        return photoFood;
    }

    public void setPhotoFood(Integer photoFood) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
