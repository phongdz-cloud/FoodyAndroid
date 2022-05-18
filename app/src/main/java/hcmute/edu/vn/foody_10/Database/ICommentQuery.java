package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Model.CommentModel;

public interface ICommentQuery extends GenericQuery<CommentModel> {
    Long insert(CommentModel commentModel);

    Integer update(CommentModel commentModel);

    Integer delete(Integer id);


    CommentModel findById(Integer id);

    List<CommentModel> findCommentByFood(Integer foodId);

    List<CommentModel> findCommentByUserAndFood(Integer userId, Integer foodId);


}
