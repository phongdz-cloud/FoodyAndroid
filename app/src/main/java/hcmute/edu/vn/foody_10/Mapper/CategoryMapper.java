package hcmute.edu.vn.foody_10.Mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.Model.CategoryModel;

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
