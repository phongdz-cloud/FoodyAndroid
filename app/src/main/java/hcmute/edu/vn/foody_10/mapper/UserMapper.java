package hcmute.edu.vn.foody_10.mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.signup.User;

public class UserMapper implements RowMapper {
    @Override
    public Object mapRow(Cursor cs) {
        User user = new User();
        user.setId(cs.getInt(0));
        user.setName(cs.getString(1));
        user.setEmail(cs.getString(2));
        user.setPassword(cs.getString(3));
        user.setAvatar(cs.getBlob(4));
        return user;
    }
}
