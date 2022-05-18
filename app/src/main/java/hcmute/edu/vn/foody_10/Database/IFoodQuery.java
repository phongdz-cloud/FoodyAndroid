package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Model.FoodModel;

public interface IFoodQuery extends GenericQuery<FoodModel> {
    Long insert(FoodModel foodModel);

    Integer update(FoodModel foodModel);

    Integer delete(Integer id);

    List<FoodModel> findAll();

    List<FoodModel> findFoodByCodeCategory(String code);

    FoodModel findById(Integer id);

    List<FoodModel> findFoodByUser(Integer userId);

}
