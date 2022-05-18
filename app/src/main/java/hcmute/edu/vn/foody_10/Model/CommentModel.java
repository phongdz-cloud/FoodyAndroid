package hcmute.edu.vn.foody_10.Model;

public class CommentModel {
    private Integer id;
    private String message;
    private Long dateTime;
    private Integer userId;
    private Integer productId;

    public CommentModel() {
    }

    public CommentModel(Integer id, String message, Long dateTime, Integer userId, Integer productId) {
        this.id = id;
        this.message = message;
        this.dateTime = dateTime;
        this.userId = userId;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
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
}
