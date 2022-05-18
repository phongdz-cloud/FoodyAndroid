package hcmute.edu.vn.foody_10.Database;

import hcmute.edu.vn.foody_10.Model.CategoryModel;

public interface ICategoryQuery extends GenericQuery<CategoryModel> {
    Long insert(CategoryModel categoryModel);

    CategoryModel findByCode(String code);

    CategoryModel findById(Integer id);
}
