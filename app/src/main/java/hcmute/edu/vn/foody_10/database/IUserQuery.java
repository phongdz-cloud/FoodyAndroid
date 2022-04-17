package hcmute.edu.vn.foody_10.database;

import hcmute.edu.vn.foody_10.signup.User;

public interface IUserQuery extends GenericQuery<User> {

    Long insert(User user);

    Integer updatePassword(User user);

    Integer updateOnlyPhoto(User user);

    Integer updatePhotoAndName(User user);

    User findById(Integer id);

    User findByEmail(String email);

    User findByUserEmailAndPassword(String email, String password);
}
