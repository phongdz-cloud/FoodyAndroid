package hcmute.edu.vn.foody_10.Mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.Model.RestaurantModel;

public class RestaurantMapper implements RowMapper<RestaurantModel> {
    @Override
    public RestaurantModel mapRow(Cursor cs) {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(cs.getInt(0));
        restaurantModel.setUserId(cs.getInt(1));
        restaurantModel.setRestaurantPhoto(cs.getInt(2));
        restaurantModel.setName(cs.getString(3));
        restaurantModel.setDescription(cs.getString(4));
        restaurantModel.setDate_time(cs.getString(5));
        restaurantModel.setRangePrice(cs.getString(6));
        return restaurantModel;
    }
}
