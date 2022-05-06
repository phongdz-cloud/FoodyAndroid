package hcmute.edu.vn.foody_10.mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.foods.FoodModel;

public class FoodMapper implements RowMapper<FoodModel> {

    @Override
    public FoodModel mapRow(Cursor cs) {
        FoodModel foodModel = new FoodModel();
        foodModel.setId(cs.getInt(0));
        foodModel.setPhotoFood(cs.getBlob(1));
        foodModel.setFoodName(cs.getString(2));
        foodModel.setFoodDescription(cs.getString(3));
        foodModel.setPrice(cs.getFloat(4));
        foodModel.setCategoryId(cs.getInt(5));
        return foodModel;
    }
}
