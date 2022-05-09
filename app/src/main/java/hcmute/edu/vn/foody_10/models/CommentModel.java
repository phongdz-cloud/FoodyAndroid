package hcmute.edu.vn.foody_10.models;

public class CommentModel {
    private String message;
    private long dateTime;

    public CommentModel() {
    }

    public CommentModel(String message, long dateTime) {
        this.message = message;
        this.dateTime = dateTime;
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

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
