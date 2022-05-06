package hcmute.edu.vn.foody_10.mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.foods.CategoryModel;

public class CategoryMapper implements RowMapper {
    @Override
    public Object mapRow(Cursor cs) {
        CategoryModel category = new CategoryModel();
        category.setId(cs.getInt(0));
        category.setName(cs.getString(1));
        category.setCode(cs.getString(2));
        return category;
    }
}
