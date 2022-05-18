package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Mapper.RestaurantMapper;
import hcmute.edu.vn.foody_10.Model.RestaurantModel;

public class RestaurantQuery extends AbstractQuery<RestaurantModel> implements IRestaurantQuery {

    private static IRestaurantQuery instance = null;

    public static IRestaurantQuery getInstance() {
        if (instance == null) {
            instance = new RestaurantQuery();
        }
        return instance;
    }

    @Override
    public Long insert(RestaurantModel restaurantModel) {
        final String sql = "INSERT into restaurant VALUES(null, ?, ?, ?, ?, ?, ?)";
        return insert(sql, restaurantModel.getUserId(), restaurantModel.getRestaurantPhoto(), restaurantModel.getName()
                , restaurantModel.getDescription(), restaurantModel.getDate_time(), restaurantModel.getRangePrice());
    }

    @Override
    public List<RestaurantModel> findAll() {
        final String sql = "SELECT * FROM restaurant";
        return query(sql, new RestaurantMapper());
    }

    @Override
    public RestaurantModel findRestaurantByUserId(Integer userId) {
        final String sql = "SELECT * FROM restaurant WHERE user_id = " + userId;
        List<RestaurantModel> results = query(sql, new RestaurantMapper());
        return results != null ? results.get(0) : null;
    }
}
