package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Model.RestaurantModel;

public interface IRestaurantQuery extends GenericQuery<RestaurantModel> {
    Long insert(RestaurantModel restaurantModel);

    List<RestaurantModel> findAll();

    RestaurantModel findRestaurantByUserId(Integer userId);
}
