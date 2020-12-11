package kostadin.ecommers.fishing.web.controllers;

import kostadin.ecommers.fishing.domain.models.binding.CategoryAddBindingModel;
import kostadin.ecommers.fishing.domain.models.service.CategoryServiceModel;
import kostadin.ecommers.fishing.domain.models.view.CategoryViewModel;
import kostadin.ecommers.fishing.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView addCategory() {
        return view("category/add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView addCategoryConfirm(@ModelAttribute CategoryAddBindingModel model) {
        CategoryServiceModel categoryServiceModel = this.modelMapper.map(model, CategoryServiceModel.class);
        this.categoryService.addCategory(categoryServiceModel);
        return redirect("/categories/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView getCategories(ModelAndView modelAndView) {
        modelAndView.addObject(
                "categories", this.categoryService
                .findAllCategories()
                .stream()
                .map(c->this.modelMapper.map(c, CategoryViewModel.class))
                .collect(Collectors.toList()));
        return view("category/all-categories", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject("model", this.modelMapper
                .map(this.categoryService
                        .findCategoryById(id), CategoryViewModel.class));
        return view("/category/edit-category", modelAndView);
    }


    @PostMapping("edit/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView editCategoryConfirm(@PathVariable String id,@ModelAttribute CategoryAddBindingModel model) {
        categoryService.editCategory(id, modelMapper.map(model,CategoryServiceModel.class));
        return redirect("/categories/all");
    }

    @GetMapping("delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView deleteCategory (@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject(
                "model", this.modelMapper.map(this.categoryService.findCategoryById(id),CategoryViewModel.class));
        return view("category/delete-category", modelAndView);

    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView deleteCategoryConfirm (@PathVariable String id) {
        this.categoryService.deleteCategory(id);
        return redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories () {
        return this.categoryService
                .findAllCategories()
                .stream()
                .map(c->this.modelMapper
                        .map(c,CategoryViewModel.class))
                .collect(Collectors.toList());
    }
}
