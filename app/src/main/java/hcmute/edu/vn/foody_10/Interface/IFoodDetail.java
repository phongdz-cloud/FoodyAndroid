package hcmute.edu.vn.foody_10.Interface;

import hcmute.edu.vn.foody_10.Model.CommentModel;

public interface IFoodDetail {
    void editComment(CommentModel commentModel);
    void deleteComment(Integer commentId);
}
