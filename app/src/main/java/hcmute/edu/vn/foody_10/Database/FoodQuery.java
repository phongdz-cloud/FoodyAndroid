package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Model.CategoryModel;
import hcmute.edu.vn.foody_10.Mapper.FoodMapper;
import hcmute.edu.vn.foody_10.Model.FoodModel;

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
        final String sql = "INSERT INTO food VALUES(null, ?, ?, ?, ?, ?, ?)";
        return insert(sql, foodModel.getPhotoFood(), foodModel.getFoodName(), foodModel.getFoodDescription(),
                foodModel.getPrice(), foodModel.getUserId(), foodModel.getCategoryId());
    }

    @Override
    public Integer update(FoodModel foodModel) {
        final String sql = "UPDATE food SET photo_food = ?, food_name = ?, food_description = ?, price = ?, user_id = ?, category_id = ? WHERE id = ?";
        return update(sql, foodModel.getPhotoFood(), foodModel.getFoodName(),
                foodModel.getFoodDescription(), foodModel.getPrice(), foodModel.getUserId(),
                foodModel.getCategoryId(), foodModel.getId());
    }

    @Override
    public Integer delete(Integer id) {
        final String sql = "DELETE FROM food WHERE id = ?";
        return delete(sql,id);
    }

    @Override
    public List<FoodModel> findAll() {
        final String sql = "SELECT * FROM food";
        return query(sql, new FoodMapper());
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

    @Override
    public List<FoodModel> findFoodByUser(Integer userId) {
        final String sql = "SELECT * FROM food where user_id = " + userId;
        return query(sql, new FoodMapper());
    }
}
