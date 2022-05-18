package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Mapper.UserMapper;
import hcmute.edu.vn.foody_10.Model.UserModel;

public class UserQuery extends AbstractQuery<UserModel> implements IUserQuery {
    private static UserQuery instance = null;

    public static UserQuery getInstance() {
        if (instance == null) {
            instance = new UserQuery();
        }
        return instance;
    }

    @Override
    public Long insert(UserModel userModel) {
        final String sql = "INSERT INTO user VALUES(null, ?, ?, ?, ?, ?, ?, ?)";
        return insert(sql, userModel.getName(), userModel.getEmail(), userModel.getPassword(), userModel.getAvatar(),
                userModel.getPhone(), userModel.getAddress(), userModel.getCreditCard());
    }

    @Override
    public Integer updatePassword(UserModel userModel) {
        final String sql = "UPDATE user SET password = ? WHERE id = ?";
        return update(sql, userModel.getPassword(), userModel.getId());
    }

    @Override
    public Integer updateOnlyPhoto(UserModel userModel) {
        final String sql = "UPDATE user SET avatar = ? WHERE id = ?";
        return update(sql, userModel.getAvatar(), userModel.getId());
    }

    @Override
    public Integer updatePhotoAndInfo(UserModel userModel) {
        final String sql = "UPDATE user SET avatar = ? , name = ? , phone = ? , address = ? WHERE id = ?";
        return update(sql, userModel.getAvatar(), userModel.getName(), userModel.getPhone(), userModel.getAddress(), userModel.getId());
    }

    @Override
    public Integer updateCreditCardUser(UserModel userModel) {
        final String sql = "UPDATE user SET credit_card = ? WHERE id = ?";
        return update(sql, userModel.getCreditCard(), userModel.getId());
    }

    @Override
    public UserModel findById(Integer id) {
        final String sql = "SELECT * FROM user WHERE id = " + id;
        return findById(sql, new UserMapper());
    }

    @Override
    public UserModel findByPhone(String phone) {
        final String sql = "SELECT * FROM user WHERE phone = '" + phone + "'";
        List results = query(sql, new UserMapper());
        if (results == null) {
            return null;
        }
        return results.size() > 0 ? (UserModel) results.get(0) : null;
    }

    @Override
    public UserModel findByEmail(String email) {
        final String sql = "SELECT * FROM user WHERE email = '" + email + "' ";
        List results = query(sql, new UserMapper());
        return results.size() > 0 ? (UserModel) results.get(0) : null;
    }

    @Override
    public UserModel findByUserEmailAndPassword(String email, String password) {
        final String sql = "SELECT * FROM user WHERE email = '" + email + "' and password = '" + password + "' ";
        final List results = query(sql, new UserMapper());
        if (results == null) {
            return null;
        }
        return results.size() > 0 ? (UserModel) results.get(0) : null;
    }
}
