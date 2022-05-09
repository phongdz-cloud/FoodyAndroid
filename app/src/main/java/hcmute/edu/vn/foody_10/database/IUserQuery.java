package hcmute.edu.vn.foody_10.database;

import hcmute.edu.vn.foody_10.models.User;

public interface IUserQuery extends GenericQuery<User> {

    Long insert(User user);

    Integer updatePassword(User user);

    Integer updateOnlyPhoto(User user);

    Integer updatePhotoAndInfo(User user);

    Integer updateCreditCardUser(User user);

    User findById(Integer id);

    User findByPhone(String phone);

    User findByEmail(String email);

    User findByUserEmailAndPassword(String email, String password);


}
