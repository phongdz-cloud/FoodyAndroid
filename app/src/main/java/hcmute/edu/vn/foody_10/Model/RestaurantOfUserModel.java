package hcmute.edu.vn.foody_10.Model;

public class RestaurantOfUserModel {
    private Integer id;
    private Integer resId;
    private Integer userId;

    public RestaurantOfUserModel() {
    }

    public RestaurantOfUserModel(Integer id, Integer resId, Integer userId) {
        this.id = id;
        this.resId = resId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
