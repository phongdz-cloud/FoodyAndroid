package hcmute.edu.vn.foody_10.Model;

import java.io.Serializable;

public class RestaurantModel implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer restaurantPhoto;
    private String name;
    private String description;
    private String date_time;
    private String rangePrice;

    public RestaurantModel() {
    }

    public RestaurantModel(Integer id, Integer userId, Integer restaurantPhoto, String name, String description, String date_time, String rangePrice) {
        this.id = id;
        this.userId = userId;
        this.restaurantPhoto = restaurantPhoto;
        this.name = name;
        this.description = description;
        this.date_time = date_time;
        this.rangePrice = rangePrice;
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

    public Integer getRestaurantPhoto() {
        return restaurantPhoto;
    }

    public void setRestaurantPhoto(Integer restaurantPhoto) {
        this.restaurantPhoto = restaurantPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getRangePrice() {
        return rangePrice;
    }

    public void setRangePrice(String rangePrice) {
        this.rangePrice = rangePrice;
    }
}
