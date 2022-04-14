package hcmute.edu.vn.foody_10.database;

import java.util.List;

import hcmute.edu.vn.foody_10.common.QueryType;
import hcmute.edu.vn.foody_10.mapper.UserMapper;
import hcmute.edu.vn.foody_10.signup.User;

public class UserQuery extends AbstractQuery<User> implements IUserQuery {
    private static UserQuery instance = null;

    public static UserQuery getInstance() {
        if (instance == null) {
            instance = new UserQuery();
        }
        return instance;
    }

    @Override
    public Long insert(User user) {
        return (Long) query("INSERT INTO user VALUES(null, ?, ?, ?, ?)", null, QueryType.INSERT,
                user.getName(), user.getEmail(), user.getPassword(), user.getAvatar());
    }

    @Override
    public User findByEmail(String email) {
        List<User> results = (List<User>) query("SELECT * FROM user WHERE email = ? ", new UserMapper(), QueryType.SELECT, email);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public User findByUserEmailAndPassword(String email, String password) {
        List<User> results = (List<User>) query("SELECT * FROM user WHERE email = '" + email + "' and password = '" + password + "' ", new UserMapper(), QueryType.SELECT, email, password);
        return results.isEmpty() ? null : results.get(0);
    }
}
