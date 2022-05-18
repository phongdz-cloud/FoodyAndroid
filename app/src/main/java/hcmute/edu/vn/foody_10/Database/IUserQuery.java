package hcmute.edu.vn.foody_10.Database;

import hcmute.edu.vn.foody_10.Model.UserModel;

public interface IUserQuery extends GenericQuery<UserModel> {

    Long insert(UserModel userModel);

    Integer updatePassword(UserModel userModel);

    Integer updateOnlyPhoto(UserModel userModel);

    Integer updatePhotoAndInfo(UserModel userModel);

    Integer updateCreditCardUser(UserModel userModel);

    UserModel findById(Integer id);

    UserModel findByPhone(String phone);

    UserModel findByEmail(String email);

    UserModel findByUserEmailAndPassword(String email, String password);


}
