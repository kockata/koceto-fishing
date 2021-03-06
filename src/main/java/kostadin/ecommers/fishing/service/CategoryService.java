package kostadin.ecommers.fishing.service;

import kostadin.ecommers.fishing.domain.models.service.CategoryServiceModel;

import java.util.List;


public interface CategoryService {
    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    CategoryServiceModel findCategoryById (String id);

    CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel);

    CategoryServiceModel deleteCategory (String id);
}
