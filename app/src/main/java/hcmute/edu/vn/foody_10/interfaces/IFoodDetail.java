package hcmute.edu.vn.foody_10.interfaces;

import hcmute.edu.vn.foody_10.models.CommentModel;

public interface IFoodDetail {
    void editComment(CommentModel commentModel);
    void deleteComment(Integer commentId);
}
