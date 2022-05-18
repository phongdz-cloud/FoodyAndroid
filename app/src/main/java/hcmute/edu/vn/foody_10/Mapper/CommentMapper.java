package hcmute.edu.vn.foody_10.Mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.Model.CommentModel;

public class CommentMapper implements RowMapper<CommentModel> {
    @Override
    public CommentModel mapRow(Cursor cs) {
        CommentModel commentModel = new CommentModel();
        commentModel.setId(cs.getInt(0));
        commentModel.setMessage(cs.getString(1));
        commentModel.setDateTime((long) cs.getInt(2));
        commentModel.setUserId(cs.getInt(3));
        commentModel.setProductId(cs.getInt(4));
        return commentModel;
    }
}
