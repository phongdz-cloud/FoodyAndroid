package hcmute.edu.vn.foody_10.database;

import java.util.List;

import hcmute.edu.vn.foody_10.foods.CategoryModel;
import hcmute.edu.vn.foody_10.mapper.CategoryMapper;

public class CategoryQuery extends AbstractQuery<CategoryModel> implements ICategoryQuery {
    private static ICategoryQuery instance = null;

    public static ICategoryQuery getInstance() {
        if (instance == null) {
            instance = new CategoryQuery();
        }
        return instance;
    }

    @Override
    public Long insert(CategoryModel categoryModel) {
        final String sql = "INSERT INTO category VALUES(null, ?, ?)";
        return insert(sql, categoryModel.getName(), categoryModel.getCode());
    }


    @Override
    public CategoryModel findByCode(String code) {
        final String sql = "SELECT * FROM category WHERE code = '" + code + "' ";
        List results = query(sql, new CategoryMapper());
        return results.size() > 0 ? (CategoryModel) results.get(0) : null;
    }

    @Override
    public CategoryModel findById(Integer id) {
        final String sql = "SELECT * FROM category WHERE id = " + id;
        return findById(sql, new CategoryMapper());
    }
}
