package hcmute.edu.vn.foody_10.database;

import java.util.List;

import hcmute.edu.vn.foody_10.models.FoodModel;
import hcmute.edu.vn.foody_10.models.User;

public interface IFoodQuery extends GenericQuery<FoodModel> {
    Long insert(FoodModel foodModel);

    Integer update(FoodModel foodModel);

    List<FoodModel> findFoodByCodeCategory(String code);

    FoodModel findById(Integer id);

    List<FoodModel> findFoodByUser(Integer userId);

}
