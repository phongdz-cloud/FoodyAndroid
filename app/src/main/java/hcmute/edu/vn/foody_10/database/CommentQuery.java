package hcmute.edu.vn.foody_10.database;

import java.util.List;

import hcmute.edu.vn.foody_10.mapper.CommentMapper;
import hcmute.edu.vn.foody_10.models.CommentModel;

public class CommentQuery extends AbstractQuery<CommentModel> implements ICommentQuery {
    private static ICommentQuery instance = null;

    public static ICommentQuery getInstance() {
        if (instance == null) {
            instance = new CommentQuery();
        }
        return instance;
    }

    @Override
    public Long insert(CommentModel commentModel) {
        final String sql = "INSERT INTO comment VALUES(null, ?, ?, ?, ?)";
        return insert(sql, commentModel.getMessage(), commentModel.getDateTime()
                , commentModel.getUserId(), commentModel.getProductId());
    }

    @Override
    public Integer update(CommentModel commentModel) {
        return null;
    }

    @Override
    public CommentModel findById(Integer id) {
        final String sql = "SELECT * FROM comment WHERE id = " + id;
        return findById(sql, new CommentMapper());
    }

    @Override
    public List<CommentModel> findCommentByFood(Integer foodId) {
        final String sql = "SELECT * FROM comment WHERE product_id = " + foodId;
        return query(sql, new CommentMapper());
    }

    @Override
    public List<CommentModel> findCommentByUserAndFood(Integer userId, Integer foodId) {
        final String sql = "SELECT * FROM comment WHERE user_id = " + userId + " AND product_id = " + foodId;
        return query(sql, new CommentMapper());
    }
}
