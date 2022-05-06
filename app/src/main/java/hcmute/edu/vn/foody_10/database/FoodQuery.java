package hcmute.edu.vn.foody_10.database;

import java.util.List;

import hcmute.edu.vn.foody_10.foods.CategoryModel;
import hcmute.edu.vn.foody_10.foods.FoodModel;
import hcmute.edu.vn.foody_10.mapper.FoodMapper;

public class FoodQuery extends AbstractQuery<FoodModel> implements IFoodQuery {

    private static IFoodQuery instance = null;

    private ICategoryQuery categoryQuery = CategoryQuery.getInstance();

    public static IFoodQuery getInstance() {
        if (instance == null) {
            instance = new FoodQuery();
        }
        return instance;
    }

    @Override
    public Long insert(FoodModel foodModel) {
        final String sql = "INSERT INTO food VALUES(null, ?, ?, ?, ?, ?)";
        return insert(sql, foodModel.getPhotoFood(), foodModel.getFoodName(), foodModel.getFoodDescription(),
                foodModel.getPrice(), foodModel.getCategoryId());
    }

    @Override
    public List<FoodModel> findFoodByCodeCategory(String code) {
        CategoryModel categoryQueryByCode = categoryQuery.findByCode(code);
        if (categoryQueryByCode != null) {
            final String sql = "SELECT * FROM food WHERE category_id = " + categoryQueryByCode.getId();
            return query(sql, new FoodMapper());
        }
        return null;
    }

    @Override
    public FoodModel findById(Integer id) {
        final String sql = "SELECT * FROM food WHERE id =" + id;
        return findById(sql, new FoodMapper());
    }
}
