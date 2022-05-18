package hcmute.edu.vn.foody_10.Mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.Model.UserModel;

public class UserMapper implements RowMapper {
    @Override
    public Object mapRow(Cursor cs) {
        UserModel userModel = new UserModel();
        userModel.setId(cs.getInt(0));
        userModel.setName(cs.getString(1));
        userModel.setEmail(cs.getString(2));
        userModel.setPassword(cs.getString(3));
        userModel.setAvatar(cs.getBlob(4));
        userModel.setPhone(cs.getString(5));
        userModel.setAddress(cs.getString(6));
        userModel.setCreditCard(cs.getString(7));
        return userModel;
    }
}
