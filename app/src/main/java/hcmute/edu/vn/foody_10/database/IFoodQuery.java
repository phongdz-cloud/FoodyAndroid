package hcmute.edu.vn.foody_10.database;

import java.util.List;

import hcmute.edu.vn.foody_10.foods.FoodModel;

public interface IFoodQuery extends GenericQuery<FoodModel>{
    Long insert(FoodModel foodModel);

    List<FoodModel> findFoodByCodeCategory(String code);

    FoodModel findById(Integer id);

}
