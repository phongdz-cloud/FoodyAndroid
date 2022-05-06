package hcmute.edu.vn.foody_10.database;

import hcmute.edu.vn.foody_10.foods.CategoryModel;

public interface ICategoryQuery extends GenericQuery<CategoryModel> {
    Long insert(CategoryModel categoryModel);

    CategoryModel findByCode(String code);

    CategoryModel findById(Integer id);
}
