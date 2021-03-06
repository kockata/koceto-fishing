package kostadin.ecommers.fishing.service.implementation;

import kostadin.ecommers.fishing.domain.entities.Category;
import kostadin.ecommers.fishing.domain.models.service.CategoryServiceModel;
import kostadin.ecommers.fishing.repository.CategoryRepository;
import kostadin.ecommers.fishing.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(c->this.modelMapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);
        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel findCategoryById(String id) {
        Category category = this.categoryRepository.findCategoriesById(id).orElseThrow(()->new IllegalArgumentException());
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel) {
        Category category = this.categoryRepository.findCategoriesById(id).orElseThrow(()->new IllegalArgumentException());
        category.setName(categoryServiceModel.getName());
        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel deleteCategory(String id) {
        Category category = this.categoryRepository.findCategoriesById(id).orElseThrow(()-> new IllegalArgumentException());
        this.categoryRepository.delete(category);
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }
}
